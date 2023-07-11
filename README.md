Приложение "Займы" с использованием API https://shiftlab.cft.ru:7777/swagger-ui/
___________________________
ветка 15_Final_project - при рабочем API

ветка mock_API - при нерабочем API
__________________________

Типичный сценарий: Пользователь регистрируется в приложении, после чего осуществляет вход. 
Видит информацию, поясняющую что ему делать далее. 
Поняв, как все работает, пользователь оформляет заявку на займ, указав необходимые данные. Если 
заявка оформлена успешно, пользователь видит экран с успехом и пояснение что ему делать дальше.
Зайдя в приложение повторно пользователь видит список оформленных займов и их статусы. Он 
может перейти в каждую конкретную заявку и посмотреть подробную информацию о ней.

![screen](https://github.com/VictoriaChrl/loansApp/assets/121154416/06427597-d5ab-4dc8-ba85-ad4040a45a8a)

________________
Стек технологий:

Retrofit2; 

Dagger; 

EncryptedSharedPreferences; 

Clean Architecture + MVVM + UDF; 

Kotlin coroutines; 

Navigation component


_______________
Особенности UI:

SwipeRefreshLayout; 

Shimmer анимации; 

ViewPager

Темная тема
