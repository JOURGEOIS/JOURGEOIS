<template>
  <div id="vue-app">
    <form action="" @submit.prevent="submit">
      <input
        ref="inputNewTodoTitleRef"
        type="text"
        placeholder="할일을 입력해주세요."
      />
      <input type="submit" value="추가" />
    </form>

    <div>
      <div v-for="todo in todos" :key="todo.id">
        {{ todo.id }}
        /
        <span>
          <span v-show="!todo.extra__modifyStatus">
            {{ todo.title }}
          </span>
          <span v-show="todo.extra__modifyStatus">
            <form style="display: inline;" @submit.prevent="modifyTodo(todo)">
              <input
                type="text"
                v-model="todo.extra__inputModifyTitleValue"
                placeholder="수정된 할일을 입력해주세요."
              />
              <input type="submit" value="수정" />
            </form>
          </span>
        </span>
        /
        <span @click="deleteTodo(todo.id)">삭제</span>
        /
        <span>
          <span
            v-show="!todo.extra__modifyStatus"
            @click="showModifyTodo(todo)"
          >
            수정
          </span>
          <span v-show="todo.extra__modifyStatus" @click="hideModifyTodo(todo)">
            수정취소
          </span>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
console.clear()
const VueApp = {
  setup() {
    let lastTodoId = 0
    const inputNewTodoTitleRef = Vue.ref()
    const todos = Vue.ref([])

    const deleteTodo = (deleteTodoId) => {
      const deleteTodoIndex = todos.value.findIndex(
        (todo) => todo.id == deleteTodoId,
      )

      todos.value.splice(deleteTodoIndex, 1)
    }

    const addTodo = (newTodoTitle) => {
      const id = lastTodoId + 1
      todos.value.push({
        id,
        title: newTodoTitle,
        extra__inputModifyTitleValue: '',
        extra__modifyStatus: false,
      })

      lastTodoId++
    }

    const showModifyTodo = (todo) => {
      todo.extra__inputModifyTitleValue = todo.title
      todo.extra__modifyStatus = true
    }

    const hideModifyTodo = (todo) => {
      todo.extra__modifyStatus = false
    }

    const modifyTodo = (todo) => {
      if (todo.extra__inputModifyTitleValue.length == 0) {
        alert('수정된 할일을 입력해주세요.')
        return
      }

      todo.title = todo.extra__inputModifyTitleValue
      hideModifyTodo(todo)
    }

    const submit = () => {
      inputNewTodoTitleRef.value.value = inputNewTodoTitleRef.value.value
      const inputNewTodoTitle = inputNewTodoTitleRef.value

      inputNewTodoTitle.value = inputNewTodoTitle.value.trim()

      if (inputNewTodoTitle.value.length > 0) {
        addTodo(inputNewTodoTitle.value)
      }

      inputNewTodoTitle.value = ''
    }

    return {
      todos,
      inputNewTodoTitleRef,
      submit,
      modifyTodo,
      deleteTodo,
      showModifyTodo,
      hideModifyTodo,
    }
  },
}
const vueAppBuilder = Vue.createApp(VueApp)
const vueApp1 = vueAppBuilder.mount('#vue-app')
</script>

<style scoped></style>