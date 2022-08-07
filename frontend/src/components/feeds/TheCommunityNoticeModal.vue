<template>
  <modal-basic
    modal-color="white"
    v-if="serviceUseModalStatus"
    @offModal="toggleServiceUseModal"
  >
    <div class="modal-content">
      <section class="modal-content-section">
        <p>본 이용규칙은 주류주아 서비스 이용 약관 중 일부로, 주류주아 이용자들은 동의한 사항입니다.</p>
        <article>
					<h1>제 4 장 : 서비스 이용</h1>
          <h2>제 14 조 (이용자의 게시물)</h2>
					<div class="m1">
						주류주아 운영진은 이용자의 글을 임의대로 삭제하지 않습니다. 운영진은
						이용자가 게시하거나 등록하는 서비스내의 내용물이 다음 각 호에
						해당한다고 판단되는 경우에만 사전통지 없이 이동 및 수정을 할 수
						있습니다.
					</div>

					<div class="m1">
						1. 다른 이용자 또는 제3자의 명예를 손상시키거나 불이익을 주는 행위를
						한 경우
					</div>
					<div class="m1">
						2. 게시물이 기술적인 오류로 이용자의 정상적인 이용에 방해를 주는
						경우
					</div>
					<div class="m1">
						3. 이용자에 의해서 적정 수준의 신고 요청이나 추천을 받은 경우
						<div class="m2">
							ㄱ. 신고 요청이란 해당 기능이 있는 게시판에서 게시 글 하단에
							신고요청을 통해 할 수 있습니다.
						</div>
						<div class="m2">
							ㄴ. 해당 게시판에 정해진 횟수 이상 받은 글은 비공개 처리됩니다.
						</div>
					</div>
					<div class="m1">
						4. 공공질서 및 범죄적 행위에 결부된다고 객관적으로 인정되는 내용일
						경우
					</div>
					<div class="m1">
						5. 주류주아 운영진이 규정한 게시 기간을 초과한 경우
					</div>
					<div class="m1">6. 게시판의 성격에 맞지 않는 경우</div>
					<div class="m1">7. 특정 이용자를 비방, 음해하는 경우</div>
					<div class="m1">8. 기타 주류주아의 운영 목적에 위배되는 경우</div>

					<h2>제 15 조 (게시물의 저작권)</h2>
					<div class="m1">
						서비스에 게재된 자료에 대한 권리는 다음 각 호와 같습니다.
					</div>
					<div class="m1">
						1.게시물에 대한 책임은 게시자에게 있으며 등록되는 모든 글과 자료는
						서비스내의 게재권을 갖습니다.
					</div>
					<div class="m1">
						2.이용자는 서비스를 이용하여 얻은 정보를 가공, 판매하는 행위 등
						서비스에 게재된 자료를 주류주아 이외의 곳에서 사용할 수 없습니다.
					</div>
					<div class="m1">
						3.게시물에 댓글이 달리면 수정은 불가능하며 삭제만 가능합니다.
					</div>
        </article>

        <article>
					<h1>제 5 장 : 제재 및 이용제한</h1>
					<h2>제 18 조 (탈퇴 및 이용제한)</h2>
					<div class="m1">
						1. 탈퇴는 언제든지 회원 정보 수정 메뉴에서 가능합니다.
					</div>
					<div class="m1">
						2. 탈퇴 전 작성된 글의 id 정보는 자동으로 삭제되나 게시한 글이나
						댓글은 삭제되지 않습니다.
					</div>
					<div class="m1">
						3. 제14조 조항을 빈번히 위반하는 이용자의 경우 사전 통보 없이 차단
						될 수 있습니다.
					</div>
					<div class="m1">
						4. 탈퇴 후 재가입시 해당 정보로 재인증이 1년간 보류됩니다.
					</div>
					<div class="m1">
						5. 영구 강등을 당했을 경우 최대 1년간 재인증이 제한됩니다.
					</div>
					<div class="m1">
						6. 주류주아에서는 '일간 베스트' 나 '워마드' 등 사회적 통념을
						부정하는, 극단적 성향의 사이트 이용자의 활동을 금지하고 있으며,
						사이트의 이미지와 사용자 간의 분쟁 예방을 위해 강등 조치가 따를 수
						있음을 명시하는 바입니다.
					</div>
        </article>
      </section>
    </div>
  </modal-basic>
</template>

<script setup lang="ts">
import ModalBasic from "@/components/basics/ModalBasic.vue";
import { useStore } from "vuex";
import { computed } from "vue";

const store = useStore();
const serviceUseModalStatus = computed(
	() => store.getters["signup/getServiceUseModalStatus"]
);

const toggleServiceUseModal = () => {
	store.dispatch("signup/toggleServiceUseModal");
};
</script>

<style scoped lang="scss">
.modal-content {
	@include flex(column);
	justify-content: center;
	align-items: center;
	gap: 36px;
	width: 300px;

	@media (max-width: 300px) {
		width: 80vw;
	}

	$title: 15px;
	$main: 13px;
	article {
		margin-bottom: 16px;
	}
  p {
		@include font($title, $fw-black);

  }
	h1 {
		padding: 0;
		margin: 16px 0;
		@include font($title, $fw-bold);
		color: $main-color;
	}

	h2 {
		padding: 0;
		margin: 16px 0;
		@include font($main, $fw-bold);
	}

	.m1 {
		margin: 10px 0 10px 0;
		@include font($main, $fw-medium);
	}

	.m2 {
		margin: 5px 0 10px 5px;
		@include font($main);
	}
}
</style>