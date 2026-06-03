import { describe, expect, it } from 'vitest'
import { mount } from '@vue/test-utils'
import SectionForm from './SectionForm.vue'

describe('SectionForm', () => {
  it('emits a trimmed section payload with meetings', async () => {
    const wrapper = mount(SectionForm)

    await wrapper.find('input[placeholder="A"]').setValue(' B ')
    await wrapper.find('input[placeholder="Dr. Rivera"]').setValue(' Dr. Patel ')
    await wrapper.find('input[placeholder="Science 204"]').setValue(' Lab 3 ')
    await wrapper.find('select').setValue('HYBRID')
    await wrapper.find('input[type="time"]').setValue('10:00')
    await wrapper.findAll('input[type="time"]')[1].setValue('11:15')
    await wrapper.find('form').trigger('submit.prevent')

    expect(wrapper.emitted('submit')?.[0]).toEqual([
      {
        sectionCode: 'B',
        instructor: 'Dr. Patel',
        location: 'Lab 3',
        modality: 'HYBRID',
        meetings: [
          {
            dayOfWeek: 'MONDAY',
            startTime: '10:00',
            endTime: '11:15',
          },
        ],
      },
    ])
  })

  it('loads an existing section for editing', () => {
    const wrapper = mount(SectionForm, {
      props: {
        section: {
          id: 4,
          sectionCode: 'A',
          instructor: 'Dr. Rivera',
          location: 'Science 204',
          modality: 'IN_PERSON',
          meetings: [
            {
              id: 9,
              dayOfWeek: 'WEDNESDAY',
              startTime: '14:00',
              endTime: '15:15',
            },
          ],
        },
      },
    })

    expect(wrapper.find('input[placeholder="A"]').element.value).toBe('A')
    expect(wrapper.text()).toContain('Edit Section')
    expect(wrapper.find('select').element.value).toBe('IN_PERSON')
    expect(wrapper.find('input[type="time"]').element.value).toBe('14:00')
  })
})
