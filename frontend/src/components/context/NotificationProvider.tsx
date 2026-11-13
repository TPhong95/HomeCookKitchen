import {useEffect, useRef, useState, type ReactNode} from "react";
import {NotificationContext} from "./NotificationContext.tsx";
import {type User} from "./UserContext.tsx";
import {useUserContext} from "./useUserContext.tsx";
import {type NotificationStruct} from "../shared/notificationinterface.ts";
import {sendJson} from "../util/api.ts";

const POLL_INTERVAL_MS = 10_000;
const FLASH_DURATION_MS = 3_000;

const NOTIFICATION_URLS = {
  chef: "http://localhost:8100/api/chef/notification",
  customer: "http://localhost:8100/api/customer/notification",
} as const;

function notificationListUrl(user: User): string {
  return user.role === "chef"
    ? `${NOTIFICATION_URLS.chef}/chefId/${user.id}`
    : `${NOTIFICATION_URLS.customer}/customerId/${user.id}`;
}

function sameNotifications(a: NotificationStruct[], b: NotificationStruct[]): boolean {
  return a.length === b.length && a.every((n, i) => n.id === b[i].id);
}

export function NotificationProvider({children}: { children: ReactNode }) {
  const {user} = useUserContext();
  const [notifications, setNotifications] = useState<NotificationStruct[]>([]);
  const [isFlashing, setIsFlashing] = useState(false);
  const previousCountRef = useRef(0);

  useEffect(() => {
    previousCountRef.current = 0;
    if (user === null) return;

    const currentUser = user;
    let cancelled = false;
    let flashTimeout: ReturnType<typeof setTimeout> | undefined;

    async function poll() {
      try {
        const res = await fetch(notificationListUrl(currentUser));
        if (!res.ok) {
          console.log(`Notification request failed: ${res.status}`);
          return;
        }
        const data: unknown = await res.json();
        if (cancelled) return; // User logged out or switched while waiting

        const unread = (Array.isArray(data) ? data as NotificationStruct[] : [])
          .filter(n => !n.isRead)
          .sort((a, b) => new Date(b.time).getTime() - new Date(a.time).getTime());

        if (unread.length > previousCountRef.current) {
          setIsFlashing(true);
          clearTimeout(flashTimeout);
          flashTimeout = setTimeout(() => setIsFlashing(false), FLASH_DURATION_MS);
        }
        previousCountRef.current = unread.length;

        setNotifications(prev => sameNotifications(prev, unread) ? prev : unread);
      } catch (error) {
        console.log(error);
      }
    }

    poll();
    const intervalId = setInterval(poll, POLL_INTERVAL_MS);

    return () => {
      cancelled = true;
      clearInterval(intervalId);
      clearTimeout(flashTimeout);
      setNotifications([]);
      setIsFlashing(false);
    };
  }, [user]);

  const markAsRead = async (id: number) => {
    if (user === null) return;
    await sendJson<unknown>("PATCH", `${NOTIFICATION_URLS[user.role]}/${id}`);
    setNotifications(prev => prev.filter(n => n.id !== id));
    previousCountRef.current = Math.max(0, previousCountRef.current - 1);
  };

  return (
    <NotificationContext value={{
      notifications,
      unreadCount: notifications.length,
      isFlashing,
      markAsRead,
    }}>
      {children}
    </NotificationContext>
  );
}