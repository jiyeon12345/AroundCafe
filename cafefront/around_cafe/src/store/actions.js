import {
  FETCH_CAFE_BOARD_LIST,
  FETCH_CAFE_BOARD,
  FETCH_MENU_LISTS,
  FETCH_CAFE_IMG_LISTS,
  FETCH_REVIEW_LIST,
  FETCH_REVIEW,
  FETCH_LIKES_LIST,
  FETCH_QNA_LIST,
  FETCH_QNA_LISTS,
  FETCH_LIKE,
  FETCH_MY_HELPS_LIST,
} from "./mutation-types"

import axios from "axios"

export default {
  fetchcafeBoardList({ commit }) {
    return axios.get("http://localhost:7777/cafe/list").then((res) => {
      commit(FETCH_CAFE_BOARD_LIST, res.data)
      //console.log(res)
    })
  },
  fetchcafeBoard({ commit }, membNo) {
    return axios
      .get(`http://localhost:7777/cafe/mypage/read/${membNo}`)
      .then((res) => {
        commit(FETCH_CAFE_BOARD, res.data)
      })
  },

  fetchMenuLists({ commit }) {
    return axios.get("http://localhost:7777/menu/list").then((res) => {
      commit(FETCH_MENU_LISTS, res.data)
    })
  },

  fetchCafeImgLists({ commit }, cafeNo) {
    return axios
      .get(`http://localhost:7777/cafe/mypageImg/${cafeNo}`)
      .then((res) => {
        commit(FETCH_CAFE_IMG_LISTS, res.data)
      })
  },

  fetchReviewList({ commit }, cafeNo) {
    return axios
      .get(`http://localhost:7777/cafe/review/list/${cafeNo}`)
      .then((res) => {
        commit(FETCH_REVIEW_LIST, res.data)
      })
  },
  fetchReview({ commit }, reviewNo) {
    return axios
      .get(`http://localhost:7777/cafe/review/list/${reviewNo}`)
      .then((res) => {
        commit(FETCH_REVIEW, res.data)
      })
  },
  fetchLikesList({ commit }, reviewNo) {
    return axios
      .get(`http://localhost:7777/cafe/like/list/${reviewNo}`)
      .then((res) => {
        commit(FETCH_LIKES_LIST, res.data)
      })
  },
  fetchLike({ commit }, { reviewNo, membNo }) {
    return axios
      .get(`http://localhost:7777/cafe/like/${reviewNo}/${membNo}`)
      .then((res) => {
        commit(FETCH_LIKE, res.data)
      })
  },
  fetchMyHelpsList({ commit }, membNo) {
    return axios
      .get(`http://localhost:7777/cafe/like/list/my/${membNo}`)
      .then((res) => {
        commit(FETCH_MY_HELPS_LIST, res.data)
      })
  },

  fetchQnAList({ commit }, qnaNo) {
    return axios
      .get(`http://localhost:7777/qna/memberRead/${qnaNo}`)
      .then((res) => {
        commit(FETCH_QNA_LIST, res.data)
      })
  },

  fetchQnALists({ commit }, membNo) {
    return axios
      .get(`http://localhost:7777/qna/memberList/${membNo}`)
      .then((res) => {
        commit(FETCH_QNA_LISTS, res.data)
      })
  },
}
