<!DOCTYPE html>
<html lang="ch">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
          name="viewport">
    <meta content="ie=edge" http-equiv="X-UA-Compatible">
    <title>登录跳转中</title>
</head>
<body>
登录中..
<script>
    const response = '${response}';
    const frontUrl = '${redirectUrl}';
    window.onload = function () {
        window.opener.postMessage(response, frontUrl);
        window.close();
    }
</script>
</body>
</html>