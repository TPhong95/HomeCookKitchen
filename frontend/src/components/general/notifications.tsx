import styles from "./notifications.module.css";
import {Banner} from "./banner.tsx";
import {type Role} from "../context/UserContext.tsx";
import {useUserContext} from "../context/useUserContext.tsx";
import {useNotificationContext} from "../context/useNotificationContext.tsx";
import {formatDateTime} from "../util/time.ts";
import toast from "react-hot-toast";

export function Notifications({role}: { role: Role }) {
  const {user} = useUserContext();
  const {notifications, markAsRead} = useNotificationContext();

  const handleMarkAsRead = async (id: number) => {
    try {
      await markAsRead(id);
    } catch (error) {
      toast.error(error instanceof Error ? error.message : "Something went wrong");
    }
  };

  return (
    <div className={styles["notifications-layout"]}>
      <Banner/>
      <div className={`${styles["notifications-wrapper"]} ${role === "customer" ? styles.customer : ""}`}>
        <div className={styles["notifications-top-banner"]}>
          <h1>Notifications</h1>
        </div>

        <div className={styles["all-notifications-container"]}>
          {user?.role !== role ? (
            <p>Not logged in as a {role}. Pick a {role} on the front page.</p>
          ) : (
            <>
              {notifications.length === 0 && <p>No unread notifications.</p>}
              {notifications.map((notification) => (
                <div
                  className={styles["notification-item-container"]}
                  key={notification.id}
                >
                  <div className={styles["notification-info-container"]}>
                    <div className={styles["notification-message"]}>{notification.message}</div>
                    <div className={styles["notification-time"]}>{formatDateTime(notification.time)}</div>
                  </div>
                  <button
                    className={styles["mark-as-read-btn"]}
                    onClick={() => handleMarkAsRead(notification.id)}
                  >
                    Mark as read
                  </button>
                </div>
              ))}
            </>
          )}
        </div>
      </div>
    </div>
  );
}