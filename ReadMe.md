<!-- **************************************************
********** Area : General *****************************
************************************************** -->

## Giới thiệu

- Demo sử dụng thư viên quảng cáo của vcc dành cho android
- Version thư viện quảng cáo đang sử dụng : ***1.0.12-dev***

## Thay đổi qua các phiên bản
- Các thay đổi trong phiên bản 1.0.10
    - Thay đổi cách trả ra cho client từ 
      ```
      List<String> zoneIds -> List<AdsManager.AdsInfo> adsInfo
      ```
    - Trả ra **ext** để cho bên client xử lý
    - Kiểm tra **tag** trước khi thực hiện các công việc khác
    
- Các thay đổi trong phiên bản 1.0.11
    - Minify và proguard code
    
- Các thay đổi trong phiên bản 1.0.12
    - Bỏ không sử dụng thư viện Gson
    - Fix bug "Undefined" ở phiên bản 1.0.11
    
- Các thay đổi trong phiên bản 1.0.13
    - Thêm hàm trả ra khi quảng cáo bắt đầu được load và khi load hoàn thành trong **AdsManagerCallback**
      ```
      @Override
      public void loadAdsStart(String id, String requestId, String zoneId) {
          super.loadAdsStart(id, requestId, zoneId);
      }
    
      @Override
      public void loadAdsFinish(String id, String requestId, String zoneId) {
          super.loadAdsFinish(id, requestId, zoneId);
      }
      ```
    - Thêm demo phần true view
    
## Dependency

- *gson* : [github](https://github.com/google/gson)
- *glide* : [github](https://github.com/bumptech/glide)
- *dataBinding*: [google](https://developer.android.com/topic/libraries/data-binding)
- *android-gif-drawable*: [github](https://github.com/koral--/android-gif-drawable)
- *android* :
    - *appcompat*
    - *material*
    - *constraintlayout*
    - *lifecycle*
    - *swiperefreshlayout*

<!-- **************************************************
********** Area : Plugin ******************************
************************************************** -->

## Cách sử dụng

- Xem tại các hosting sử dựng git
    - [Gitlab](https://about.gitlab.com/)
    - [Github](https://github.com/)
- Clone về và mở bằng browser
    - [Chrome](https://chrome.google.com/webstore/detail/markdown-preview-plus/febilkbfcbhebfnokafefeacimjdckgl)
    - [FireFox](https://addons.mozilla.org/en-US/firefox/addon/markdown-viewer-chrome/)

<!-- **************************************************
********** Area : Content *****************************
************************************************** -->

## Nội dung trong demo

- **Test Request** : Nhập thông tin zoneIds để request xem quảng cáo
    - Input ( zoneIds - được cũng cấp bởi admicro )
    - Response Ads View
    - Log
- **Demo View** :
    - Tổng hợp :
        - *All in one* : Example tổng hợp các tính năng trong 1 màn hình ( pending )
        - *True view* : Test trueview cho phần ads web ( pending )
    - Quảng cáo đơn - Format :
        - *Popup* ( processing )
        - *In page* ( processing )
        - *Cat fish* ( pending )
    - Quảng cáo Native
        - *Home*
        - *Detail* ( processing )
    - Quảng cáo Web
        - *Top*
        - *BillBoard*
        - *Medium*
        - *Adx Native*
        - *Adx Sponsor* : 180x200 & 300x250
        - *Lotus Shop Chat*
        - *Poster*
        - *Inpage*
        - *Native* : normal, vieo, livestream
        - *Deep Link*
        - *No Deep Link*

<!-- **************************************************
********** Area : Requirement *************************
************************************************** -->

## Requirement

- Sdk min : 23
- Gradle : 7.0.3
- Permission :
    - **INTERNET** - Sử dụng để lấy thông tin quảng cáo ( Bắt buộc phải có )
    - **WRITE_EXTERNAL_STORAGE** - Dùng để ghi log ra file
    - **ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION** - Dùng để lấy tọa độ vị trí người dùng

<!-- **************************************************
********** Area : Tutorial ****************************
************************************************** -->

## Hướng dẫn sử dụng sdk
0. **Note**
    - Gọi khi lần đầu cài app ( **bắt buộc** , dùng cho việc click vào quảng cáo bay sang app nhưng app chưa cài )
    ```
    https://ul.admicro.vn/dynamic-link/get?device_id=[deviceid]&ifa=[ifa]&app_id=[app_store_id]
    ```
    - Những hàm trả về **id** như *request*, do trả về ở các nơi đăng ký thông qua  AdsManagerCallback. Để tránh phải xử lý nhiều thì nên **check tag và requestId** trước khi xử lý công việc tiếp theo
    - Các thành phần lưu ý trong **ext**:
        - *non_inpage* ( "0" : Ads cần vẽ dưới dạng inpage, "1" : Ads cần vẽ không ẩn dưới bài viết )
    
1. **Gradle:**
    - Thêm maven từ sohames
    ```
    allprojects {
        repositories {
            google()
            mavenCentral()
            maven {
                url "https://sohames.jfrog.io/artifactory/vcc-sdk-ads-gradle-dev-local/"
            }
        }
    }
    ```
    - Thêm gradle dependency của vccorp ads sdk
    ```
    dependencies {
        implementation 'vcc.viv.ads:vcc-sdk-ads:1.0.10'
    }
    ```
2. **Khởi tạo manager:**
    - Get instance, không create new, từ phiên bản 1.0.10 sẽ private lại constructor
    ```
    AdsManager adsManager = AdsManager.getInstance();
    ```
    - Khởi tạo InitializeParameter :
    ```
    InitializeParameter parameter = new InitializeParameter()
        .setCore(activity, appId, version)
        .setLogger(true, AdsLogger.Level.verbose, "AdsSDK");
        .setDeviceInfo(deviceId);      
    ```
        - **setCore ( Bắt buộc )** : 
            - *activity* - Activity : Activity 
            - *appId* - String : uuid của bên app, đc cấp bằng server hoặc tự gen
            - *VERSION_CODE* - String : version của bản code hiện tại
        - **setLog** : 
            - *loggerConsole* - boolean : cho phép ghi log hay không
            - *LoggerLevel* - AdsLogger.Level : level logger có thể in. Tương quan giữa các level : verbose < debug < info < warning < error
            - *LoggerTag* - String : Tiền tố log, dùng cho filter log
        - **setDeviceInfo** : 
            - *deviceID* : device id truyền vào để sử dụng cho request ads, nếu không truyền thì sdk tự lấy
    - Khởi tạo thông tin cho AdsManager thông qua InitializeParameter. ***Đây là hàm bắt buộc phải
      gọi, nếu không gọi thì tất cả các hàm sử dụng đằng sau sẽ invalid***
    ```
    adsManager.initialize(parameter);
    ```
3. **Đăng kí nhận thông tin trả về**
    - Khởi tạo callback để nhận tín hiệu từ AdsManager
    ```
    AdsManagerCallback callback = new AdsManagerCallback() {
        @Override
        public void initSuccess() {
            super.initSuccess();
        }
        
        @Override
        public void initLocalSuccess() {
            super.initLocalSuccess();
        }
        
        @Override
        public void initError(String msg) {
            super.initError(msg);
        }
        
        @Override
        public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
            super.requestAdsSuccess(id, requestId, adsInfo);
        }
        
        @Override
        public void requestAdsFail(String id, String requestId, String msg) {
            super.requestAdsFail(id, requestId, msg);
        }
   
        @Override
        public void loadAdsStart(String id, String requestId, String zoneId) {
            super.loadAdsStart(id, requestId, zoneId);
        }

        @Override
        public void loadAdsFinish(String id, String requestId, String zoneId) {
            super.loadAdsFinish(id, requestId, zoneId);
        }
    };
    ```
        - **initLocalSuccess** : Khởi tạo thành công từ cache
        - **initSuccess** : Khởi tạo thành công hoàn toàn
        - **initError** : Khởi tạo AdsManager thất bại
        - **requestAdsSuccess** : Request quảng cáo thành công, adsInfo là thông tin quảng cáo được trả về với những zone thành công
        - **requestAdsSuccess** : Request quảng cáo thất bại
        - **loadAdsStart** : Bắt đầu hiển thị quảng cáo
        - **loadAdsFinish** : Quảng cáo được đã được hiển thị 
    - Đăng ký callback với AdsManager :
    ```
    adsManager.callbackRegister(tag, callback);
    ```
4. **Request ads**
    ```
    adsManager.request(id, requestId, new AdsRequestWorker.ReaderParameter(
              userId,
              zones,
              positions, url, channel
    ));
    ```
    - **id** - String : xác định vị trí request theo màn hình
    - **requestId** - String : lần request trong 1 id, xử dụng nếu màn hình request 1 zoneid nhiều
      lần
    - **userId** - String : Id user login từ bên client truyền vào nếu có
    - **zones** - List<String> : id các vùng quy hoạch quảng cáo
    - **positions** - List<String> : Ví trí quảng cáo, hiện tại không dùng, fix cứng truyền vào là
      array ["1"]
    - **url** - String : url bài báo chi tiết nếu có. Xác định ads quảng cáo theo nội dung phù hợp
      hoặc chặn không hiển thị ads trong bài.
    - **channel** - String : Channel của bài đó ví dụ link url, tham số phải được encodeURL
5. **Sử dụng view quảng cáo** : Sau khi request thành công, sử dụng id, requestId, zoneIds ( được
   trả về trong callback ) để xác định view quảng cáo
    ```
    AdsData info = adsManager.addAds(form, parent, id, requestId, zoneIds);
    ```
    - **form** - AdsConst.AdsForm : dạng quảng cáo, không truyền vào thì lấy form default là normal
    - **parent** - ViewGroup : view sẽ chứa quảng cáo
    - **id** - String : xác định vị trí request theo màn hình
    - **requestId** - String : lần request trong 1 id, xử dụng nếu màn hình request 1 zoneid nhiều
      lần
    - **zoneIds** - String : id các vùng quy hoạch quảng cáo
6. **click** : Sử dụng khi có 1 view đè lên trên quảng cáo, nhưng không muốn click xuyên qua. Ví dụ
   inpage
    ```
    toolkit.adsManager.click(event, tag, requestId, zoneId);
    ```
    - **event** - MotionEvent : event lấy trong onTouchListener
    - **id** - String : xác định vị trí request theo màn hình
    - **requestId** - String : lần request trong 1 id, xử dụng nếu màn hình request 1 zoneid nhiều
      lần
    - **zoneIds** - String : id các vùng quy hoạch quảng cáo
7. **visibility** : Sử dụng khi view chứa quảng cáo có thay đổi về view được nhìn thấy trên màn
   hình, ví dụ scroll
    ```
    toolkit.adsManager.visibility(tag, requestId, homeZoneId, isShow, percent)
    ```
    - **id** - String : xác định vị trí request theo màn hình
    - **requestId** - String : lần request trong 1 id, xử dụng nếu màn hình request 1 zoneid nhiều
      lần
    - **zoneIds** - String : id các vùng quy hoạch quảng cáo
    - **isShow** - boolean : true nếu view đang hiển thị trên màn hình
    - **percent** - double : Số % lượng content view được hiển thị trên màn hình. Truyền vào từ 0.0
      -> 1.0

<!-- **************************************************
********** Area : Contact *****************************
************************************************** -->

## Liên hệ

- Chủ sở hữu : **Vccorp**
- Phòng ban : **MySoha**
- Quản lí tài liệu :
    - SohaMes - sohames.vcc@googlemail.com
- Người phát triển :
    - **Nguyễn Anh Tú** - ♂ - Leader - tunguyenanh@vccorp.vn
    - **Nguyễn Tuấn Anh** - ♂ - Developer - nguyentuananh04@vccorp.vn