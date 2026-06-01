import { describe, expect, it } from 'vitest'
import { mount } from '@vue/test-utils'
import WeeklyCalendar from './WeeklyCalendar.vue'
import { getCalendarDays, getCalendarRange, timeToMinutes } from './calendarUtils'

const sections = [
  {
    courseId: 1,
    courseCode: 'CS 201',
    courseName: 'Data Structures',
    sectionId: 10,
    sectionCode: 'A',
    modality: 'IN_PERSON',
    meetings: [
      {
        dayOfWeek: 'MONDAY',
        startTime: '10:00',
        endTime: '11:15',
      },
    ],
  },
  {
    courseId: 2,
    courseCode: 'ONLINE 101',
    courseName: 'Async Seminar',
    sectionId: 11,
    sectionCode: 'WEB',
    modality: 'ONLINE',
    meetings: [],
  },
]

describe('WeeklyCalendar', () => {
  it('renders meeting blocks and no-fixed-time sections', () => {
    const wrapper = mount(WeeklyCalendar, {
      props: { sections },
    })

    expect(wrapper.text()).toContain('Monday')
    expect(wrapper.text()).toContain('CS 201')
    expect(wrapper.text()).toContain('10:00-11:15')
    expect(wrapper.text()).toContain('No Fixed Meeting Time')
    expect(wrapper.text()).toContain('ONLINE 101 · Section WEB')
  })

  it('includes weekend columns only when weekend meetings exist', () => {
    expect(getCalendarDays([{ dayOfWeek: 'MONDAY' }])).toEqual([
      'MONDAY',
      'TUESDAY',
      'WEDNESDAY',
      'THURSDAY',
      'FRIDAY',
    ])

    expect(getCalendarDays([{ dayOfWeek: 'SATURDAY' }])).toContain('SATURDAY')
  })

  it('computes time helpers', () => {
    expect(timeToMinutes('10:15')).toBe(615)
    expect(
      getCalendarRange([
        {
          startTime: '10:15',
          endTime: '13:45',
        },
      ]),
    ).toEqual({ startHour: 10, endHour: 14 })
  })
})
