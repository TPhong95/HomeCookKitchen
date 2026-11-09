export async function sendJson<T>(method: "POST" | "PATCH", url: string, body?: unknown): Promise<T> {
  const res = await fetch(url, {
    method,
    headers: {"Content-Type": "application/json"},
    body: body === undefined ? undefined : JSON.stringify(body),
  });

  if (!res.ok) {
    const errorBody: { detail?: string } | null = await res.json().catch(() => null);
    throw new Error(errorBody?.detail ?? `Request failed: ${res.status}`);
  }

  return res.json();
}