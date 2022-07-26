<template>
	<modal-basic
		modal-color="white"
		v-if="personalInfoUseModalStatus"
		@offModal="togglePersonalInfoUseModal"
	>
		<div class="modal-content">
			<section class="modal-content-section">
				<article class="description">
					<h1>제 1 장 : 총칙</h1>
					JOURGEOIS(이하 "주류주아")(은)는 회원님(이하 "이용자")의 개인정보를
					중요시하며, '정보통신망 이용촉진 및 정보보호에 관한 법률',
					'개인정보보호법' 등을 준수하고 있습니다. "주류주아"는
					개인정보취급방침을 통하여 "이용자"님께서 제공하시는 개인정보가 어떠한
					용도와 방식으로 이용되고 있으며, 개인정보보호를 위해 어떠한 조치가
					취해지고 있는지 알려드립니다.
				</article>

				<article>
					<h1>1. 개인정보의 수집 및 이용목적</h1>
					<div class="description">
						"주류주아"는 수집한 개인정보를 다음의 목적을 위해 활용합니다. 이용
						목적이 변경되는 경우에는 개인정보 보호법 제18조에 따라 별도의 동의를
						받는 등 필요한 조치를 이행할 예정입니다.
					</div>
					<div class="m1">
						회원 가입 및 관리
						<div class="m2">
							<div>
								회원제 서비스 이용에 따른 본인확인, 개인 식별, 불량"이용자"의
								부정 이용 방지와 비인가 사용 방지, 가입 의사 확인, 가입 및
								가입횟수 제한, 회원 제재, 고객 문의에 대한 회신, 분쟁 조정을
								위한 기록 보존, 민원처리, 고지사항 전달
							</div>
						</div>
					</div>
					<div class="m1">
						신규 서비스 개발 및 마케팅/광고에 활용
						<div class="m2">
							<div>
								신규서비스(제품) 개발 및 특화 서비스 제공, 통계학적 특성에 따른
								서비스 제공 및 광고 게재, 접속빈도 파악, 이벤트 정보 및 참여기회
								제공, 광고성 정보제공, 신규 상품 안내ㅡ "이용자"의 서비스 이용에
								대한 통계 등
							</div>
						</div>
					</div>
					<div class="m1">
						법령 및 약관 등의 이행 및 준수
						<div class="m2">
							<div>
								법령 혹은 이용약관 등에 반하여 피해를 줄 수 있는 부분을 방지
							</div>
						</div>
					</div>
				</article>

				<article>
					<h1>2. 수집하는 개인정보 항목 및 수집방법</h1>
					<h2>가. 수집하는 개인정보의 항목</h2>
					<div class="m1">
						"주류주아"가 개인정보보호를 위하여 최소한의 정보를 받으려 노력하며,
						원활한 서비스 제공과 서비스 개선을 위해 다음과 같은 정보를 수집하고
						있습니다.
					</div>
					<div class="m1">
						회원가입 및 인증, 서비스 제공을 위해 아래와 같은 개인정보를 수집하고
						있습니다.
						<div class="m2">필수항목 : 성명, 이메일, 교육정보</div>
					</div>
					<div class="m1">
						맞춤형 서비스를 제공하기 위해서 Google, Github(OAuth Service
						제공자)의 OAuth 서비스를 제공하고 있습니다. OAuth 서비스 제공자의
						OAuth 서비스를 이용하고자 할 경우 다음의 필수 정보를 입력하여야 하며
						필수항목을 입력하시지 않는다면 서비스 이용에 제한을 받을 수
						있습니다.
						<div class="m2">이용자 ID, 이메일, 성명, 프로필 이미지</div>
					</div>
					<div class="m1">
						또한, OAuth 서비스 제공자의 OAuth 서비스 이용 시 고객님의 데이터에
						액세스하고 서비스에 사용할 수 있습니다. OAuth 서비스 이용 과정에서
						자동생성 되어 저장되는 정보는 아래와 같습니다.
						<div class="m2">OAuth 서비스 제공자에게 전달받은 Token 정보</div>
						<div class="m2">
							OAuth 서비스의 이용을 원치 않는 경우 언제든지 연결 해제 버튼을
							클릭하여 연결을 해제할 수 있으며, 이 경우 저장되어 있던 Token
							정보는 삭제됩니다.
						</div>
					</div>
					<div class="m1">
						부가서비스 또는 이벤트 응모 과정에서 해당 서비스의 "이용자"에 한해서
						몇 가지 정보들이 수집 될 수 있으며, 이러한 정보 수집 시 별도의
						수집항목에 대한 동의를 받은 후 수집합니다.
					</div>
					<div class="m1">
						서비스 이용 과정이나 사업 처리 과정에서 서비스이용기록, 접속로그,
						쿠키, 접속 IP, 결제 기록, 불량이용 기록이 생성되어 수집될 수
						있습니다.
					</div>

					<h2>나. 수집방법</h2>
					<div class="m1">
						"주류주아"는 다음과 같은 방법으로 개인정보를 수집합니다.
					</div>
					<div class="m1">
						회원 가입이나 사용 중 "이용자"의 자발적 제공을 통한 수집
					</div>
					<div class="m1">자동적으로 정보를 생성하는 수집 툴을 통한 수집</div>
					<div class="m1">
						※ 정보주체의 수집이용 거부권 및 불이익 고지
						<div class="m2">
							정보주체는 "주류주아"가 수집하는 개인정보의 항목 및 수집 방법에
							동의를 거부할 수 있으며, 거부 시 "이용자"에게 제공되는 서비스
							이용의 일부가 제한될 수 있습니다.
						</div>
					</div>
				</article>

				<article>
					<h1>3. 수집한 개인정보의 위탁</h1>
					<div class="m1">
						"주류주아"는 "이용자"의 동의 없이 개인정보 처리를 외부 업체에
						제공하지 않습니다.
					</div>
				</article>

				<article>
					<h1>4. 개인정보의 보유 및 이용기간</h1>
					<div class="m1">
						"주류주아"가 개인정보를 수집하는 경우 보유기간은 동의에 의한
						회원가입 후 회원탈퇴 시까지입니다. 또한 회원탈퇴시 해당 정보를 지체
						없이 파기합니다. 단, 다음의 정보에 대해서는 아래의 이유로 명시한
						기간 동안 보존합니다.
						<div class="m2">
							"주류주아" 내부 방침에 의한 정보보호 사유
							<div class="m3">
								일반회원 개인정보 - 보존근거 : 서비스 이용 혼란 및 부정 이용
								방지
								<div class="m4">보존기간 : 1년</div>
							</div>
						</div>
					</div>
				</article>

				<article>
					<h1>5. 개인정보의 파기절차 및 방법</h1>
					<div class="m1">
						"주류주아"는 원칙적으로 개인정보 수집 및 이용목적이 달성된 후에는
						해당 정보를 지체 없이 파기합니다. 파기절차 및 방법은 다음과
						같습니다.
					</div>
					<div class="m1">
						가. 파기절차
						<div class="m2">
							회원가입 등을 위해 입력하신 정보는 목적이 달성된 후 내부 방침 및
							기타 관련 법령에 의한 정보보호 사유에 따라(보유 및 이용기간 참조)
							일정 기간 저장된 후 파기됩니다.
						</div>
					</div>
					<div class="m1">
						나. 파기방법
						<div class="m2">
							전자적 파일형태로 저장된 개인정보는 기록을 재생할 수 없는 기술적
							방법을 사용하여 삭제합니다.
						</div>
					</div>
				</article>

				<article>
					<h1>6. "이용자"의 권리와 행사방법</h1>
					<div class="m1">
						"이용자"는 언제든지 등록되어 있는 자신의 개인정보를 조회하거나
						수정할 수 있으며 가입해지를 요청할 수도 있습니다.
					</div>
					<div class="m1">
						"이용자"들의 개인정보 조회,수정을 위해서는 "개인정보변경"(또는
						"회원정보수정" 등)을 통해, 가입해지(동의철회)를 위해서는
						"고객센터"를 통해 본인 확인 절차를 거치신 후 직접 열람, 정정 또는
						탈퇴가 가능합니다.
					</div>
					<div class="m1">
						혹은 개인정보관리책임자에게 전화 또는 이메일로 연락하시면 지체 없이
						조치하겠습니다.
					</div>
					<div class="m1">
						귀하가 개인정보의 오류에 대한 정정을 요청하신 경우에는 정정을
						완료하기 전까지 당해 개인정보를 이용 또는 제공하지 않습니다. 또한
						잘못된 개인정보를 제3자에게 이미 제공한 경우에는 정정 처리결과를
						제3자에게 지체 없이 통지하여 정정이 이루어지도록 하겠습니다.
					</div>
					<div class="m1">
						"주류주아"는 "이용자"의 요청에 의해 해지 또는 삭제된 개인정보는
						“개인정보의 보유 및 이용기간"에 명시된 바에 따라 처리하고 그 외의
						용도로 열람 또는 이용할 수 없도록 처리하고 있습니다.
					</div>
				</article>

				<article>
					<h1>7. 개인정보 자동수집 장치의 설치, 운영 및 그 거부에 관한 사항</h1>
					<div class="m1">
						"주류주아"는 "이용자"의 정보를 수시로 저장하고 불러오는
						‘쿠키(cookie)’ 등을 사용합니다. 쿠키란 "주류주아"의 웹사이트를
						운영하는데 이용되는 서버가 귀하의 브라우저에 보내는 작은 텍스트
						파일로서 "이용자"의 컴퓨터 하드디스크에 저장됩니다.쿠키는 개인을
						식별하는 정보를 자동적/능동적으로 수집하지 않으며, "이용자"는
						언제든지 이러한 쿠키의 저장을 거부하거나 삭제할 수 있습니다.
					</div>
					<div class="m1">
						쿠키 등 사용 목적
						<div class="m2">
							회원과 비회원의 접속 빈도나 방문시간 등을 분석, "이용자"의 취향과
							관심분야를 파악 및 자취추적, 각종 이벤트 참여 정도 및 방문 회수
							파악 등을 통한 타겟 마케팅 및 개인 맞춤 서비스, 로그인 서비스 제공
						</div>
						<div class="m2">
							"이용자"는 쿠키 설치에 대한 선택권을 가지고 있습니다. 따라서
							"이용자"는 웹 브라우저의 옵션을 선택함으로써 모든 쿠키를
							허용하거나, 쿠키가 저장될 때마다 확인을 거치거나, 아니면 모든
							쿠키의 저장을 거부할 수도 있습니다.
						</div>
					</div>
					<div class="m1">
						쿠키 설정 거부 방법
						<div class="m2">
							쿠키 설정을 거부하는 방법으로는 "이용자"가 사용하는 웹 브라우저의
							옵션을 설정함으로써 모든 쿠키를 허용하거나, 쿠키가 저장될 때 마다
							확인을 거치거나 혹은 모든 쿠키의 저장을 거부 할 수도 있습니다.
						</div>
						<div class="m2">
							쿠키 설치 허용 여부를 지정하는 방법은 다음과 같습니다.
							<div class="m3">
								Internet Explorer의 경우
								<div class="m4">
									웹 브라우저 상단의 [도구] 메뉴에서 [인터넷 옵션]을 선택합니다.
								</div>
								<div class="m4">[개인정보]탭을 클릭합니다.</div>
								<div class="m4">
									[사이트(S)],[고급(V)]를 통하여 사이트 별 쿠키 설정이
									가능합니다.
								</div>
							</div>
							<div class="m3">
								Chrome의 경우
								<div class="m4">
									웹 브라우저 우측의 [설정] 메뉴를 선택합니다.
								</div>
								<div class="m4">
									화면 하단의 [고급 설정 표시]를 선택 합니다.
								</div>
								<div class="m4">
									[개인정보]탭에서 [콘텐츠 설정]을 선택해서 쿠키 설정을 할 수
									있습니다.
								</div>
							</div>
						</div>
						<div class="m2">
							쿠키의 저장을 거부하는 경우에는 로그인이 필요한 일부서비스 이용에
							어려움이 있을 수 있습니다.
						</div>
					</div>
				</article>

				<article>
					<h1>8. 개인정보의 안전성 확보조치 및 대책</h1>
					<div class="m1">
						"주류주아"는 「개인정보보호법」 제29조에 따라 "이용자"들의
						개인정보를 취급함에 있어 개인정보가 분실, 도난, 누출, 변조 또는
						훼손되지 않도록 다음과 같이 안전성 확보에 필요한 기술적, 관리적
						조치를 하고 있습니다.
					</div>
					<div class="m1">
						기술적 대책
						<div class="m2">
							비밀번호 암호화
							<div class="m3">
								서비스 회원 아이디(ID)의 비밀번호는 암호화되어 저장 및 관리되고
								있어 본인만이 알고 있으며, 개인정보의 확인 및 변경도 비밀번호를
								알고 있는 본인에 의해서만 가능합니다.
							</div>
						</div>
						<div class="m2">
							해킹 등에 대비한 대책
							<div class="m3">
								"주류주아"는 해킹이나 컴퓨터 바이러스 등에 의해 "이용자"의
								개인정보가 유출되거나 훼손되는 것을 막기 위해 최선을 다합니다.
							</div>
							<div class="m3">
								개인정보의 훼손에 대비해서 자료를 수시로 백업하고 있고, 최신
								백신프로그램을 이용하여 "이용자"들의 개인정보나 자료가
								누출되거나 손상되지 않도록 방지하고 있으며, 암호화통신 등을
								통하여 네트워크상에서 개인정보를 안전하게 전송할 수 있도록
								합니다.
							</div>
							<div class="m3">
								그리고 침입차단시스템을 이용하여 외부로부터의 무단 접근을
								통제하고 있으며, 기타 시스템적으로 보안성을 확보하기 위한 가능한
								모든 기술적 장치를 갖추려 노력합니다.
							</div>
						</div>
					</div>
					<div class="m1">
						관리적 대책
						<div class="m2">
							취급 직원의 최소화 및 교육
							<div class="m3">
								"주류주아"의 개인정보관련 취급 직원은 담당자에 한정시키고 있고
								이를 위한 별도의 비밀번호를 부여하여 정기적으로 갱신하고 있으며,
								담당자에 대한 수시 교육을 통하여 "주류주아"의 개인정보취급방침의
								준수를 항상 강조하고 있습니다.
							</div>
						</div>
						<div class="m2">
							개인정보보호전담기구의 운영
							<div class="m3">
								그리고 사내 개인정보보호전담기구 등을 통하여 "주류주아"의
								개인정보취급방침의 이행사항 및 담당자의 준수여부를 확인하여
								문제가 발견될 경우 즉시 수정하고 바로 잡을 수 있도록 노력하고
								있습니다.
							</div>
						</div>
					</div>
					<div class="m1">
						단, "이용자" 본인의 부주의나 인터넷상의 문제로 ID, 비밀번호,
						주민등록번호 등 개인정보가 유출되어 발생한 문제에 대해 "주류주아"는
						일체의 책임을 지지 않습니다.
					</div>
				</article>

				<article>
					<h1>10. 기타</h1>
					<div class="m1">
						"주류주아"의 서비스 내에 링크되어 있는 웹사이트 등 타 서비스들이
						개인정보를 수집하는 행위에 대해서는 본 서담 개인정보처리방침이
						적용되지 않습니다.
					</div>
				</article>

				<article>
					<h1>11. 개인정보 처리방침의 변경에 관한 사항</h1>
					<div class="m1">
						현 개인정보처리방침 내용 추가, 삭제 및 수정이 있을 시에는 시행일
						최소 7일 전부터 "이용자"의 이메일 혹은 웹사이트를 통해 공고합니다.
					</div>
					<div class="m1">
						해당 개인정보 처리방침은 2022.09.01부터 적용 됩니다.
					</div>
				</article>
			</section>
		</div>
	</modal-basic>
</template>

<script setup lang="ts">
import ModalBasic from "@/components/basics/ModalBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { useStore } from "vuex";
import { computed } from "vue";

const store = useStore();
const personalInfoUseModalStatus = computed(
	() => store.getters["signup/getPersonalInfoUseModalStatus"]
);

const togglePersonalInfoUseModal = () => {
	store.dispatch("signup/togglePersonalInfoUseModal");
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
		margin-bottom: 50px;
	}

	h1 {
		padding: 0;
		margin: 32px 0 16px 0;
		@include font($title, $fw-bold);
		color: $main-color;
	}

	h2 {
		padding: 0;
		margin: 16px 0;
		@include font($main, $fw-bold);
	}

	.description {
		@include font($main);
	}

	.m1 {
		margin: 10px 0 10px 0;
		@include font($main, $fw-medium);
	}

	.m2 {
		margin: 5px 0 10px 5px;
		@include font($main);
	}
	.m3 {
		margin: 5px 0 10px 10px;
		@include font($main);
	}
	.m4 {
		margin: 5px 0 10px 15px;
		@include font($main);
	}
}

.logout-modal-button {
	@include flex-xy(center, center);
	gap: 20px;
	width: 100%;

	button {
		@include p-component(md);
		@include shadow-modal;
	}
}
</style>
