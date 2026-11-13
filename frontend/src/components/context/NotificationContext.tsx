import {createContext} from "react";
import {type NotificationStruct} from "../shared/notificationinterface.ts";

export interface NotificationContextValue {
  notifications: NotificationStruct[]; // Unread only, newest first
  unreadCount: number;
  isFlashing: boolean;
  markAsRead: (id: number) => Promise<void>; // Throws if the PATCH fails
}

export const NotificationContext = createContext<NotificationContextValue | null>(null);