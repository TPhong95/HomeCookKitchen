import {useContext} from "react";
import {NotificationContext, type NotificationContextValue} from "./NotificationContext.tsx";

export function useNotificationContext(): NotificationContextValue {
  const context = useContext(NotificationContext);
  if (!context) {
    throw new Error("useNotificationContext must be used inside a NotificationProvider");
  }
  return context;
}