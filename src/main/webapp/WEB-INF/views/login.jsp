<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/parts/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>마을버스파인</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/reset.css" />
    <script src="/resources/js/jquery/jquery-3.5.1.min.js"></script>
    <script src="/resources/js/jquery/jquery.mloading.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/jquery/jquery.mloading.css">
</head>
<body>
<div class="lg_container">
    <div class="lg_m_wrap">
        <div class="lg_f_wrap">
            <div class="lg_logo_wrap">
                <span><img src="/resources/images/title_gg_trns.png" /></span>
                <span class="lg_logo_title">마을버스파인</span>
                <span><img src="/resources/images/core_systems_logo.png" /></span>
            </div>
        </div>
        <div class="lg_s_wrap">
            <div class="lg_s_inner">
                <div class="lg_s_l">
                    <div class="lg_iframe_wrap">
                        <img src="/resources/images/title_gg_trns.png" />
                    </div>
                </div>
                <div class="lg_s">
                    <div class="lg_s_title">아이디 로그인</div>
                    <form id="frmObj">
                        <div class="lg_frm">
                            <div class="lg_frm_input">
                                <div>
                                    <input type="text" class="valid_input" id="id" name="id" maxlength="50" placeholder="아이디를 입력해주세요." data-empty="아이디를 입력해주세요."/>
                                </div>
                                <div>
                                    <input type="password" class="valid_input" id="password" name="password" maxlength="50" autocomplete="off" placeholder="비밀번호를 입력해주세요." data-empty="비밀번호를 입력해주세요."/>
                                </div>
                            </div>
                            <button id="btnLogin" class="lg_btn btn1" type="button">로그인</button>
                        </div>
                    </form>
                    <div>
                        <button class="lg_use_btn" type="button">이용안내</button>
                    </div>
                    <div class="lg_s_comment">
                        <div>
                            <p class="lg_s_comment_title">버스파인 사용 가능 웹 브라우저</p>
                            <p>1. Microsoft Edge</p>
                            <p>2. Google Chrome</p>
                            <p>위 웹 브라우저를 사용하셔야 오류 없이 사용 하실 수 있습니다.
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- div class="lg_privacy">
            <ul>
                <li><a href="#">개인정보처리 및 위탁에 관한 안내 약관</a></li>
                <li><a href="#">개인정보처리방침</a></li>
            </ul>
        </div -->
    </div>
</div>
</body>
<script>
document.addEventListener('keydown', function(e){
    if (e.key === 'Enter') {
        if(e.target.id === 'id') {
            sendLogin();
        } else if(e.target.id === 'password') {
            sendLogin();
        }
    }
});

document.getElementById('btnLogin').addEventListener('click', function(){
    sendLogin();
});

function sendLogin(force = false) {
    const userId = document.getElementById('id');
    const userPwd = document.getElementById('password');

    if(userId.value.trim() == ''){
        alert('아이디를 입력해 주세요.');
        userId.focus();
        return false;
    } else if(userPwd.value.trim() == '') {
        alert('비밀번호를 입력해 주세요.');
        userPwd.focus();
        return false;
    }

    const form = document.getElementById("frmObj");
    const inputs = form.querySelectorAll('input');

    inputs.forEach(input => {
        input.value = input.value.trim();
    });

    const formData = new FormData(form);
    if (force) formData.append("force", "true");

    $("body").mLoading('show');
    fetch('/login', {
        method: "POST",
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams(formData)
    })
    .then(async response => {
        try {
            let json = '';

            if(response.status != 500) {
                json = await response.json();
            }
            if(response.status == 200) {
                if(json.select){
                    console.log(json.select);
                    alert("시군구 선택");
                    location.replace(json.redirectUrl);
                } else {
                    location.replace(json.redirectUrl);
                }
                return false;
            } else if(response.status == 409) {
                if(confirm(json.message)){
                    sendLogin(true);
                }
            } else {
                if(json.message != null) {
                    alert(json.message);
                } else {
                    alert('알수 없는 오류');
                }
            }
            $("body").mLoading('hide');
        } catch(e) {
            $("body").mLoading('hide');
            console.log(e);
            alert('알수 없는 오류');
        }
    });
}
</script>
</html>

