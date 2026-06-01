import { apiRequest } from './http'

export function getPreferences() {
  return apiRequest('/api/preferences')
}

export function updatePreferences(preferences) {
  return apiRequest('/api/preferences', {
    method: 'PUT',
    body: JSON.stringify(preferences),
  })
}
