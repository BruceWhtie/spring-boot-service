$(function () {
    // Waves初始化
    Waves.displayEffect();
    // 输入框获取焦点后出现下划线
    $('.form-control').focus(function () {
        $(this).parent().addClass('fg-toggled');
    }).blur(function () {
        $(this).parent().removeClass('fg-toggled');
    });

    // 点击登录按钮
    $('#login-button').click(login);
    // 回车事件
    $('#username, #password').keypress(function (event) {
        if (13 === event.keyCode) {
            login();
        }
    });
});

// 登录请求
function login() {
    $.ajax({
        url: 'login',
        type: 'POST',
        dataType: 'json',
        data: {
            username: $('#username').val(),
            password: $('#password').val()
        },
        success: function (result) {
            if (result.success) {
                location.href = 'index.html';
            } else {
                $('#login-alert').text('登录失败，请检查账号密码是否正确！').show();
                setTimeout(function () {
                    $('#login-alert').hide();
                }, 8000);
            }
        },
        error: function () {
            $('#login-alert').text('请求出错了！').show();
            setTimeout(function () {
                $('#login-alert').hide();
            }, 8000);
        }
    });
}