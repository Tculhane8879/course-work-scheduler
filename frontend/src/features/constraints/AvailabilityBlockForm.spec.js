import { describe, expect, it } from 'vitest'
import { mount } from '@vue/test-utils'
import AvailabilityBlockForm from './AvailabilityBlockForm.vue'

describe('AvailabilityBlockForm', () => {
  it('emits a trimmed availability block payload', async () => {
    const wrapper = mount(AvailabilityBlockForm)

    await wrapper.find('input[placeholder="Work shift"]').setValue(' Campus job ')
    await wrapper.find('select').setValue('WEDNESDAY')
    await wrapper.findAll('select')[1].setValue('UNAVAILABLE')
    await wrapper.find('input[type="time"]').setValue('13:00')
    await wrapper.findAll('input[type="time"]')[1].setValue('16:30')
    await wrapper.find('form').trigger('submit.prevent')

    expect(wrapper.emitted('submit')?.[0]).toEqual([
      {
        label: 'Campus job',
        dayOfWeek: 'WEDNESDAY',
        startTime: '13:00',
        endTime: '16:30',
        blockType: 'UNAVAILABLE',
      },
    ])
  })

  it('loads an existing block for editing', () => {
    const wrapper = mount(AvailabilityBlockForm, {
      props: {
        block: {
          id: 3,
          label: 'Work',
          dayOfWeek: 'FRIDAY',
          startTime: '09:00',
          endTime: '14:00',
          blockType: 'WORK',
        },
      },
    })

    expect(wrapper.find('input[placeholder="Work shift"]').element.value).toBe('Work')
    expect(wrapper.text()).toContain('Edit Block')
    expect(wrapper.find('select').element.value).toBe('FRIDAY')
  })
})
