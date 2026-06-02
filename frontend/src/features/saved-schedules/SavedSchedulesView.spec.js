import { describe, expect, it, vi } from 'vitest'
import { flushPromises, mount } from '@vue/test-utils'
import SavedSchedulesView from './SavedSchedulesView.vue'
import { summarizeSchedule } from './savedScheduleUtils'

const savedSchedule = {
  id: 1,
  name: 'Spring option A',
  preferred: true,
  score: {
    total: 92,
    preferencePenalty: 4,
    compactnessScore: 16,
    gapScore: 10,
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
}

describe('SavedSchedulesView', () => {
  it('renders saved schedule summaries', async () => {
    vi.stubGlobal(
      'fetch',
      vi.fn(() =>
        Promise.resolve({
          ok: true,
          status: 200,
          json: () => Promise.resolve([savedSchedule]),
        }),
      ),
    )

    const wrapper = mount(SavedSchedulesView)
    await flushPromises()

    expect(wrapper.text()).toContain('Spring option A')
    expect(wrapper.text()).toContain('Preferred')
    expect(wrapper.text()).toContain('92')
    expect(wrapper.text()).toContain('CS 201')
  })

  it('summarizes schedule meetings', () => {
    expect(summarizeSchedule(savedSchedule.sections)).toEqual({
      daysOnCampus: 1,
      earliestStart: '10:00',
      latestEnd: '11:15',
      meetingCount: 1,
    })
  })
})
