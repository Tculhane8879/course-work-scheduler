import { apiRequest } from './http'

export function getCourses() {
  return apiRequest('/api/courses')
}

export function createCourse(course) {
  return apiRequest('/api/courses', {
    method: 'POST',
    body: JSON.stringify(course),
  })
}

export function updateCourse(id, course) {
  return apiRequest(`/api/courses/${id}`, {
    method: 'PUT',
    body: JSON.stringify(course),
  })
}

export function deleteCourse(id) {
  return apiRequest(`/api/courses/${id}`, {
    method: 'DELETE',
  })
}
