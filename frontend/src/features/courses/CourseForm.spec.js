import { describe, expect, it } from 'vitest'
import { mount } from '@vue/test-utils'
import CourseForm from './CourseForm.vue'

describe('CourseForm', () => {
  it('emits a trimmed create payload', async () => {
    const wrapper = mount(CourseForm)

    await wrapper.find('input[placeholder="CS 201"]').setValue(' CS 201 ')
    await wrapper.find('input[placeholder="Data Structures"]').setValue(' Data Structures ')
    await wrapper.find('select').setValue('ELECTIVE')
    await wrapper.find('form').trigger('submit.prevent')

    expect(wrapper.emitted('submit')?.[0]).toEqual([
      {
        code: 'CS 201',
        name: 'Data Structures',
        priority: 'ELECTIVE',
      },
    ])
  })

  it('loads an existing course for editing', () => {
    const wrapper = mount(CourseForm, {
      props: {
        course: {
          id: 1,
          code: 'MATH 120',
          name: 'Calculus I',
          priority: 'REQUIRED',
        },
      },
    })

    expect(wrapper.find('input[placeholder="CS 201"]').element.value).toBe('MATH 120')
    expect(wrapper.find('input[placeholder="Data Structures"]').element.value).toBe('Calculus I')
    expect(wrapper.find('select').element.value).toBe('REQUIRED')
    expect(wrapper.text()).toContain('Edit Course')
  })
})
