export function formatDateTime(time: string): string {
  return time.slice(0, 16).replace("T", " ");
}