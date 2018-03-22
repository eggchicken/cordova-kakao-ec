var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'cordova-kakao-ec', 'sendTemplate', [arg0]);
};
