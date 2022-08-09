import { Module } from 'vuex'
import { RootState } from '../index'
import axios from 'axios'
import api from '../../api/api'
import { checkBadWord } from '../../functions/checkText'
import router from '../../router'

export interface CreateFeedState {
  // * (+) 팝업 모달
  createFeedModalStatus: boolean
  createFeedModalClass: string
  // 팝업 알림
  alertStatus: boolean
  // 오류 메시지
  errorMessage: string
  // 성공 메시지
  successMessage: string

}

export const createFeed: Module<CreateFeedState, RootState> = {
  namespaced: true,

  state: {
    // * (+) 팝업 모달
    createFeedModalStatus: false,
    createFeedModalClass: '',
    // 에러 팝업 상태
    alertStatus: false,
    // 에러 메시지
    errorMessage: '',
    // 성공 메시지
    successMessage: '',

  },

  getters: {
    // * (+) 팝업 모달
    getCreateFeedModalStatus: (state) => state.createFeedModalStatus,
    getCreateFeedModalClass: (state) => state.createFeedModalClass,
    // 알럿 팝업 상태
    getAlertStatus: (state) => state.alertStatus,
    // 에러 메시지
    getErrorMessage: (state) => state.errorMessage,
    // 성공 메시지
    getSuccessMessage: (state) => state.successMessage,
  },

  mutations: {
    // * (+) 팝업 모달
    SET_CREATE_FEED_MODAL_STATUS: (state, value: boolean) => {
      state.createFeedModalStatus = value
    },
    SET_CREATE_FEED_MODAL_CLASS: (state, value: string) => {
      state.createFeedModalClass = value
    },
    // 에러 팝업
    SET_ALERT_STATUS: (state, value: boolean) => {
      state.alertStatus = value
    },
    // 에러 메시지
    SET_ERROR_MESSAGE: (state, value: string) => {
      state.errorMessage = value
    },
    // 성공 메시지
    SET_SUCCESS_MESSAGE: (state, value: string) => {
      state.successMessage = value
    },
  },

  actions: {
    // vuex 리셋
    resetCommunityData: ({ commit }) => {
      commit("SET_ALERT_STATUS", false);
      commit("SET_ERROR_MESSAGE", "");
      commit("SET_SUCCESS_MESSAGE", "");
    },

    // 알럿 팝업
    changeAlertStatus: ({ commit }, value: boolean) => {
      commit('SET_ALERT_STATUS', value)
    },

    //============== 제출
    // 일반 게시글 제출 전 유효성 검사 (빈 필드가 있는지 확인한다.)
    submitCommunityForm: ({ commit, dispatch }, data) => {
      const { description, img } = data

      // 빈필드 확인 (빈 필드가 있으면 false)
      const imgRequired = img instanceof File
      const descriptionRequired = !!description

      // 전부 true면 유효성 검사 통과!
      if (imgRequired && descriptionRequired) {
        // 비속어 유효성 검사, 공백으로 이뤄진 내용 유효성 검사
        dispatch('checkTextInput', data)
      } else {
        commit('SET_ERROR_MESSAGE', '이미지와 내용은 필수 입력입니다')
        commit('SET_ALERT_STATUS', true)
      }
    },

    checkTextInput: ({ dispatch, commit }, data) => {
      const { description } = data
      // 내용 중 빈 문자만 제출했는지 확인 (빈문자열이 있으면 false)
      const descriptionNull = description.trim().length
      console.log('type: ', descriptionNull)
      if (descriptionNull === 0) {
        commit('SET_ERROR_MESSAGE', '내용에는 빈 값을 입력할 수 없습니다')
        commit('SET_ALERT_STATUS', true)
        return
      }
      // 비속어 확인 (비속어가 있으면 true)
      const descriptionCheck = checkBadWord(description)

      if (descriptionCheck) {
        commit('SET_ERROR_MESSAGE', '부적절한 언어가 포함되었습니다')
        commit('SET_ALERT_STATUS', true)
        return
      }
      // 저장
      dispatch('saveCommunity', data)
    },

    // 저장(생성)
    saveCommunity: ({ commit, rootGetters, dispatch }, data) => {
      const { description, img } = data
      // 저장하기
      axios({
        url: api.post.postCocktail(),
        method: 'post',
        headers: {
          Authorization: rootGetters['personalInfo/getAccessToken'],
          'Content-Type': 'multipart/form-data',
        },
        data: {
          img,
          description,
        },
      })
        .then((res) => {
          console.log(res)
          // 상세 페이지로 이동
          router.replace({
            name: 'TheCommunityDescView',
            params: { feedId: res.data },
          })

          // 성공 알림
          console.log(res)
          commit('SET_SUCCESS_MESSAGE', '성공적으로 등록되었습니다')
          commit('SET_ALERT_STATUS', true)
        })
        .catch((error) => {
          console.log(error)
          if (error.response.status !== 401) {
            // 실패 팝업
            commit('SET_ERROR_MESSAGE', '잠시 후에 시도해주세요')
            commit('SET_ALERT_STATUS', true)
          } else {
            // refreshToken 재발급
            const obj = {
              func: 'createFeed/saveCommunity',
              params: data,
            }
            dispatch('personalInfo/requestRefreshToken', obj, { root: true })
          }
        })
    },
    // ================== 일반게시글 수정
    // 일반게시글 수정 시 유효성 검사
    updateCommunityForm: ({ commit, dispatch }, data) => {
      const { description, img, postId } = data
      console.log('유효성검사 data: ', data)

      // 내용 중 빈 문자만 제출했는지 확인 (빈문자열이 있으면 false)
      const descriptionNull = description.trim().length
      console.log('type: ', descriptionNull)
      if (descriptionNull === 0) {
        commit('SET_ERROR_MESSAGE', '내용에는 빈 값을 입력할 수 없습니다')
        commit('SET_ALERT_STATUS', true)
        return
      }
      // 비속어 확인 (비속어가 있으면 true)
      const descriptionCheck = checkBadWord(description)

      if (descriptionCheck) {
        commit('SET_ERROR_MESSAGE', '부적절한 언어가 포함되었습니다')
        commit('SET_ALERT_STATUS', true)
        return
      }
      // 저장
      dispatch('updateSaveCommunity', data)
    },

    // 일반게시글 수정
    updateSaveCommunity: (
      { commit, dispatch, rootGetters },
      params,
    ) => {
      const { description, img, postId } = params
      console.log(params)
      axios({
        url: api.post.postCocktail(),
        method: 'PUT',
        headers: {
          Authorization: rootGetters['personalInfo/getAccessToken'],
          'Content-Type': 'multipart/form-data',
        },
        data: params,
      })
        .then((res) => {
          console.log(res.data)
          router.replace({
            name: 'TheCommunityDescView',
            params: { feedId: res.data },
          })

          // 성공 알림
          console.log(res)
          commit('SET_SUCCESS_MESSAGE', '성공적으로 변경되었습니다')
          commit('SET_ALERT_STATUS', true)
        })
        .catch((err) => {
          console.log(err)
          if (err.response.status !== 401) {
            // 실패 팝업
            commit('SET_ERROR_MESSAGE', '잠시 후에 시도해주세요')
            commit('SET_ALERT_STATUS', true)
          } else {
            // refreshToken 재발급
            const obj = {
              func: 'createFeed/updateSaveCommunity',
              params,
            }
            dispatch('personalInfo/requestRefreshToken', obj, { root: true })
          }
        })
    },
    // * (+) 팝업 모달
    toggleCreateFeedModal: ({ commit }, value: boolean) => {
      commit('SET_CREATE_FEED_MODAL_STATUS', value)
    },
    changeCreateFeedModalClass: ({ commit }, value: string) => {
      commit('SET_CREATE_FEED_MODAL_CLASS', value)
    },
  },
}
