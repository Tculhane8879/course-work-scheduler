import { apiRequest } from './http'

export function getAvailabilityBlocks() {
  return apiRequest('/api/availability-blocks')
}

export function createAvailabilityBlock(block) {
  return apiRequest('/api/availability-blocks', {
    method: 'POST',
    body: JSON.stringify(block),
  })
}

export function updateAvailabilityBlock(id, block) {
  return apiRequest(`/api/availability-blocks/${id}`, {
    method: 'PUT',
    body: JSON.stringify(block),
  })
}

export function deleteAvailabilityBlock(id) {
  return apiRequest(`/api/availability-blocks/${id}`, {
    method: 'DELETE',
  })
}
