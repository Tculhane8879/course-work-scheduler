import { describe, expect, it, vi } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import ScheduleGeneratorView from './ScheduleGeneratorView.vue'

describe('ScheduleGeneratorView', () => {
  it('generates schedules for selected courses', async () => {
    const fetchMock = vi.fn((url) => {
      if (url === '/api/courses') {
        return Promise.resolve({
          ok: true,
          status: 200,
          json: () =>
            Promise.resolve([
              { id: 1, code: 'CS 201', name: 'Data Structures', priority: 'REQUIRED' },
            ]),
        })
      }

      return Promise.resolve({
        ok: true,
        status: 200,
        json: () =>
          Promise.resolve([
            {
              score: {
                total: 92,
                compactnessScore: 16,
                gapScore: 10,
                preferencePenalty: 4,
                maxClassesPenalty: 0,
              },
              sections: [
                {
                  courseId: 1,
                  courseCode: 'CS 201',
                  courseName: 'Data Structures',
                  sectionId: 3,
                  sectionCode: 'A',
                  instructor: 'Dr. Rivera',
                  location: 'Science 204',
                  modality: 'IN_PERSON',
                  meetings: [
                    {
                      dayOfWeek: 'MONDAY',
                      startTime: '10:00',
                      endTime: '11:15',
                    },
                  ],
                },
              ],
            },
          ]),
      })
    })
    vi.stubGlobal('fetch', fetchMock)

    const wrapper = mount(ScheduleGeneratorView)
    await flushPromises()

    await wrapper.find('input[type="checkbox"]').setValue(true)
    await wrapper.find('button.primary-button').trigger('click')
    await flushPromises()

    expect(fetchMock).toHaveBeenCalledWith(
      '/api/schedules/generate',
      expect.objectContaining({
        method: 'POST',
        body: JSON.stringify({ courseIds: [1], maxResults: 10 }),
      }),
    )
    expect(wrapper.text()).toContain('Option 1')
    expect(wrapper.text()).toContain('92 / 100')
  })
})
