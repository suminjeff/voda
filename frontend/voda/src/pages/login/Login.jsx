import React, { useState } from "react"; // eslint-disable-line no-unused-vars
import styled from "styled-components";
import vodaLogo from "/logo.svg";
import vodaLogoLetter from "/logo_letter.svg";
import btn_google from "/login_btn/btn_google.svg";
import btn_naver from "/login_btn/btn_naver.svg";
import btn_kakao from "/login_btn/btn_kakao.svg";
// import axios from "axios";
import { useNavigate } from "react-router-dom";
import { getSocialLoginUrl } from "../../services/auth";

const ImageContainer = styled.div({
  display: "flex",
  flexDirection: "column",
  justifyContent: "center",
  alignItems: "center",
});

const InstructionContainer = styled.p({
  textAlign: "center",
  fontSize: "1rem",
  fontWeight: "bold",
  marginTop: "5rem",
});

const ButtonContainer = styled.div({
  display: "flex",
  flexDirection: "row",
  justifyContent: "center",
  alignItems: "center",
  gap: "1.5rem",
  marginTop: "1.5rem",
});

const Login = () => {
  async function deleteCaches() {
    try {
      const keys = await window.caches.keys();
      console.log(keys);
      await Promise.all(keys.map((key) => caches.delete(key)));
      console.log("deleted");
    } catch (err) {
      console.log("deleteCache err: ", err);
    }
  }

  const loginHandler = (e) => {
    const link = getSocialLoginUrl(e.target.id);
    console.log(link);
    deleteCaches();
    window.location.href = link;
    deleteCaches();
  };
  const baseURL = import.meta.env.VITE_API_URL;

  return (
    <>
      <ImageContainer>
        <img
          src={vodaLogo}
          alt="logo"
          style={{
            marginTop: "5rem",
            height: "65%",
            width: "65%",
          }}
        />
        <img
          src={vodaLogoLetter}
          alt=""
          style={{
            marginTop: "1.5rem",
          }}
        />
      </ImageContainer>
      <InstructionContainer>SNS로 간편 로그인</InstructionContainer>
      <ButtonContainer>
        <img
          src={btn_google}
          alt=""
          id="google"
          onClick={loginHandler}
          style={{
            width: "3rem",
          }}
        />
        <img
          src={btn_kakao}
          alt=""
          id="kakao"
          // onClick={loginHandler}
          style={{
            width: "3rem",
          }}
        />
        <img
          src={btn_naver}
          alt=""
          id="naver"
          // onClick={loginHandler}
          style={{
            width: "3rem",
            borderRadius: "100%",
          }}
        />
      </ButtonContainer>
    </>
  );
};

export default Login;
