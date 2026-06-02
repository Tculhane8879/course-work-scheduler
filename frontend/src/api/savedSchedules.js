import { apiRequest } from './http'

export function getSavedSchedules() {
  return apiRequest('/api/saved-schedules')
}

export function createSavedSchedule(savedSchedule) {
  return apiRequest('/api/saved-schedules', {
    method: 'POST',
    body: JSON.stringify(savedSchedule),
  })
}

export function markSavedSchedulePreferred(id) {
  return apiRequest(`/api/saved-schedules/${id}/preferred`, {
    method: 'PUT',
  })
}

export function deleteSavedSchedule(id) {
  return apiRequest(`/api/saved-schedules/${id}`, {
    method: 'DELETE',
  })
}
