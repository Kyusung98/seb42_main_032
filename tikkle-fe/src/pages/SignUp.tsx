//TODO SIGNUP_001 OAUth 회원가입 페이지 구현
import styled from 'styled-components';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  // ! 로고 크기 조정하기
  .header-logo__img {
    width: 50vh;
  }

  .google-btn {
    margin: 25px;
    width: 80%;
    min-width: 184px;
    max-width: 184px;
    height: 42px;
    background-color: #fcfcfc;
    border-radius: 2px;
    box-shadow: 0 3px 4px 0 rgba(0, 0, 0, 0.2);
    cursor: pointer;
    cursor: hand;
    align-self: center;
    user-select: none;
    transition: all 400ms ease 0s;
    display: flex;
  }
  .google-btn .google-icon-wrapper {
    position: absolute;
    margin-top: 1px;
    margin-left: 1px;
    width: 40px;
    height: 40px;
    border-radius: 2px;
    user-select: none;
  }
  .google-btn .google-icon-svg {
    position: absolute;
    margin-top: 11px;
    margin-left: 11px;
    width: 18px;
    height: 18px;
    user-select: none;
  }
  .google-btn .btn-text {
    float: right;
    margin: 11px 14px 40px 40px;
    color: #757575;
    font-size: 14px;
    letter-spacing: 0.2px;
    font-family: Roboto;
    user-select: none;
  }
  .google-btn:hover {
    box-shadow: 0 3px 8px rgba(117, 117, 117, 0.5);
    user-select: none;
  }
  .google-btn:active {
    box-shadow: 0 1px 1px #757575;
    background: #f8f8f8;
    color: #fff;
    user-select: none;
  }
`;
function SignUp() {
  return (
    <Container>
      <img className="header-logo__img" src="tikkle-logo.svg" alt="logo" />
      <p>편리한 예산별 지출 관리 서비스, 티클 ✨</p>
      <div className="google-btn">
        <div className="google-icon-wrapper">
          <img
            className="google-icon-svg"
            src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"
          />
        </div>
        <p className="btn-text">
          <b>Sign in with Google</b>
        </p>
      </div>
    </Container>
  );
}

export default SignUp;
