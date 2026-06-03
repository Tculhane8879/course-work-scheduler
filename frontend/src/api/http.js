const apiBaseUrl = import.meta.env.VITE_API_BASE_URL || ''

export async function apiRequest(path, options = {}) {
  const response = await fetch(`${apiBaseUrl}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  })

  if (!response.ok) {
    const error = await response.json().catch(() => ({
      message: `Request failed with status ${response.status}`,
    }))

    throw error
  }

  if (response.status === 204) {
    return null
  }

  return response.json()
}
