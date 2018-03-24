var exec = require('cordova/exec');

var Kakao = {
    sendTemplate: function (success, error, arg) {
        exec(success, error, 'Kakao', 'sendTemplate', arg);
    }
}

module.exports = Kakao;
