        function VkAuth() {
            this.responseFunc = this.responseVk;
        }

        VkAuth.prototype.responseVk = function(result) {
            console.log(result);
        }

        VkAuth.prototype.initUser = function (responseFunction) {
            this.responseFunc = responseFunction;
            var token = cookie.GetCookie("access_token");
            if (token != "") {
                var userId = cookie.GetCookie("user_id");
                if (userId == "") {
                    this.getUserId(token, "vkAuth.responseGetUserId");
                } else {
                    this.getUserData(userId, token, "vkAuth.responseGetUserData");
                }
            }
        };

        VkAuth.prototype.getUserId = function (accessToken, callback) {
            this.performJsonpVkRequest("getProfiles", accessToken, callback);
        }

        VkAuth.prototype.getUserData = function (userId, accessToken, callback) {
            var fields = "uid,first_name,last_name,nickname,screen_name,sex,bdate,city,country,timezone,photo_200_orig";
            this.performJsonpVkRequest("users.get", accessToken, callback, "fields=" + fields + "&user_id=" + userId);
        }

        VkAuth.prototype.performJsonpVkRequest =  function (method, accessToken, callbackFunc) {
            var url = "https://api.vk.com/method/" + method;
            var script = document.createElement('script');
            script.src = url+ "?v=5.2&access_token=" + accessToken + "&callback=" + callbackFunc;
            document.getElementsByTagName("head")[0].appendChild(script);
        }

        VkAuth.prototype.performJsonpVkRequest = function (method, accessToken, callbackFunc, optionalGet) {
            var url = "https://api.vk.com/method/" + method;
            var script = document.createElement('script');
            script.src = url+ "?v=5.2&access_token=" + accessToken + "&callback=" + callbackFunc + "&" + optionalGet;
            document.getElementsByTagName("head")[0].appendChild(script);
        }

        VkAuth.prototype.responseGetUserId = function (result) {
            cookie.SetCookie("user_id", result.response[0].id);
            this.initUser(this.responseFunc);
        }

        VkAuth.prototype.responseGetUserData = function (result) {
            try {
                var data = result.response[0];
                var user = {
                    firstName: data.first_name,
                    lastName: data.last_name,
                    photo: data.photo_200_orig
                };
                this.responseFunc(user);
            } catch (err) {
                var errorCode = result.error.error_code;
                if (errorCode == 5) {
                    //Redirect
                    this.refreshVkAccessToken();
                }
            }
            return result.response[0].uid;
        }

        VkAuth.prototype.refreshVkAccessToken =  function () {
            cookie.DelCookie("access_token");
            cookie.DelCookie("user_id");

            var client_id = 3913319;
            var scope = 12;
            var redirect_uri = "http://localhost:8090/vk-auth";
            var response_type = "code";
            var display = "page";
            location.href = "https://oauth.vk.com/authorize?client_id=" + client_id + "&scope=" + scope + "&redirect_uri=" + redirect_uri + "&response_type=" + response_type + "&display=" + display;
        }