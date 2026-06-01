import { apiRequest } from './http'

export function getSections(courseId) {
  return apiRequest(`/api/courses/${courseId}/sections`)
}

export function createSection(courseId, section) {
  return apiRequest(`/api/courses/${courseId}/sections`, {
    method: 'POST',
    body: JSON.stringify(section),
  })
}

export function updateSection(sectionId, section) {
  return apiRequest(`/api/sections/${sectionId}`, {
    method: 'PUT',
    body: JSON.stringify(section),
  })
}

export function deleteSection(sectionId) {
  return apiRequest(`/api/sections/${sectionId}`, {
    method: 'DELETE',
  })
}
