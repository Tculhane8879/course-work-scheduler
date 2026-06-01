import { describe, expect, it } from 'vitest'
import { mount } from '@vue/test-utils'
import SchedulePreferenceForm from './SchedulePreferenceForm.vue'

describe('SchedulePreferenceForm', () => {
  it('emits the current preference settings', async () => {
    const wrapper = mount(SchedulePreferenceForm)
    const checkboxes = wrapper.findAll('input[type="checkbox"]')

    await checkboxes[0].setValue(true)
    await checkboxes[3].setValue(true)
    await wrapper.find('input[type="number"]').setValue(4)
    await wrapper.findAll('input[type="number"]')[1].setValue(45)
    await wrapper.find('form').trigger('submit.prevent')

    expect(wrapper.emitted('submit')?.[0]).toEqual([
      {
        avoidEarlyMorning: true,
        avoidLateEvening: false,
        preferCompactSchedule: true,
        preferNoFridayClasses: true,
        maxClassesPerDay: 4,
        minimumGapMinutes: 45,
      },
    ])
  })
})
