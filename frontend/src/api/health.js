import { apiRequest } from './http'

export function getHealth() {
  return apiRequest('/api/health')
}
