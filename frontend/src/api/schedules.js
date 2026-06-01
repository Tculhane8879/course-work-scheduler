import { apiRequest } from './http'

export function generateSchedules(request) {
  return apiRequest('/api/schedules/generate', {
    method: 'POST',
    body: JSON.stringify(request),
  })
}
