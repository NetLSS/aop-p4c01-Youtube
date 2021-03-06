# Youtube 앱 만들어보기

보다 자세한 내용을 저의 [블로그 글](https://whyprogrammer.tistory.com/603)에서도 보실 수 있습니다.

# TODO

- MotionLayout 이용하여 화면 전환 UI 구성하기
- 영상 목록 API 만들기
- 영상 목록 기본 구조 만들기
- MotionLayout 과 RecyclerView 사이에 스크롤 가능하게 하기
- ExoPlayer를 이용하여 동영상 재생하기


# 결과화면

![1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fm3Wl6%2Fbtq9ynMlyEw%2FOinbZsUTZJalHydEeVLKS1%2Fimg.png)



![2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F5Fh5j%2Fbtq9weI9fln%2F9CZi8Bn4SfEUyzeWrTkKu1%2Fimg.png)

![3](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fw8fEM%2Fbtq9qlPZbV9%2FDPtnJhBcaM5DTzhnEHm2S1%2Fimg.png)


# 사용 기술

- **MotionLayout** 사용하기
- **Exoplayer** 사용하기



### MotionLayout

- ConstraintLayout 라이브러리의 일부 (서브 클래스)
- https://developer.android.com/training/constraint-layout/motionlayout/examples?hl=ko
- 레이아웃 전환과 UI 이동, 크기 조절 및 애니메이션에 사용
- 이기정님의 파트 4, 챕터 4, *OTT 앱 인트로 따라하기* 에서 더 자세히 후술

- @lss
- 사용자의 ui 인터렉션, 이동에 다라 뷰 크기 위치 변화를 주면서 애니메이션을 쉽게 구현 가능

### ExoPlayer

- Google이 Android SDK 와 별도로 배포되는 오픈소스 프로젝트
- 오디오 및 동영상 재생 가능
- 오디오 및 동영상 재생 관련 강력한 기능들 포함
- 유튜브 앱에서 사용하는 라이브러리
- https://exoplayer.dev/hello-world.html

- @lss
- 오디오 동영상 재생에 쉽고 편히하게 사용 가능.

---

### Youtube

Retrofit 을 이용하여 영상 목록을 받아와 구성함

MotionLayout 을 이용하여 유튜브 영상 플레이어 화면전환 애니메이션을 구현함.

영상 목록을 클릭하여 ExoPlayer 를 이용하여 영상을 재생할 수 있음.

---

### 메인 레이아웃

- 동영상 리스트는 리사이클러뷰
- 바닥 홈 바텀 네비게이션 뷰
- 스크롤 가능한 뷰는 프레임레이아웃(-프레그먼트)


### demo video
- https://gist.github.com/deepakpk009/99fd994da714996b296f11c3c371d5ee
  
### mocky
- https://run.mocky.io/v3/66f12ec7-a2e6-4070-b7cc-7f3563fbe962

### exoplayer
https://developer.android.com/guide/topics/media/exoplayer
https://exoplayer.dev/hello-world.html

```json
{
    "videos": [
        {
            "description": "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.\n\nLicensed under the Creative Commons Attribution license\nhttps://www.bigbuckbunny.org",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "subtitle": "By Blender Foundation",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
            "title": "Big Buck Bunny"
        },
        {
            "description": "The first Blender Open Movie from 2006",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            "subtitle": "By Blender Foundation",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg",
            "title": "Elephant Dream"
        },
        {
            "description": "HBO GO now works with Chromecast -- the easiest way to enjoy online video on your TV. For when you want to settle into your Iron Throne to watch the latest episodes. For $35.\nLearn how to use Chromecast with HBO GO and more at google.com/chromecast.",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            "subtitle": "By Google",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg",
            "title": "For Bigger Blazes"
        },
        {
            "description": "Introducing Chromecast. The easiest way to enjoy online video and music on your TV—for when Batman's escapes aren't quite big enough. For $35. Learn how to use Chromecast with Google Play Movies and more at google.com/chromecast.",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            "subtitle": "By Google",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg",
            "title": "For Bigger Escape"
        },
        {
            "description": "Introducing Chromecast. The easiest way to enjoy online video and music on your TV. For $35.  Find out more at google.com/chromecast.",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            "subtitle": "By Google",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerFun.jpg",
            "title": "For Bigger Fun"
        },
        {
            "description": "Introducing Chromecast. The easiest way to enjoy online video and music on your TV—for the times that call for bigger joyrides. For $35. Learn how to use Chromecast with YouTube and more at google.com/chromecast.",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            "subtitle": "By Google",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerJoyrides.jpg",
            "title": "For Bigger Joyrides"
        },
        {
            "description": "Introducing Chromecast. The easiest way to enjoy online video and music on your TV—for when you want to make Buster's big meltdowns even bigger. For $35. Learn how to use Chromecast with Netflix and more at google.com/chromecast.",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            "subtitle": "By Google",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerMeltdowns.jpg",
            "title": "For Bigger Meltdowns"
        },
        {
            "description": "Sintel is an independently produced short film, initiated by the Blender Foundation as a means to further improve and validate the free/open source 3D creation suite Blender. With initial funding provided by 1000s of donations via the internet community, it has again proven to be a viable development model for both open 3D technology as for independent animation film.\nThis 15 minute film has been realized in the studio of the Amsterdam Blender Institute, by an international team of artists and developers. In addition to that, several crucial technical and creative targets have been realized online, by developers and artists and teams all over the world.\nwww.sintel.org",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            "subtitle": "By Blender Foundation",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg",
            "title": "Sintel"
        },
        {
            "description": "Smoking Tire takes the all-new Subaru Outback to the highest point we can find in hopes our customer-appreciation Balloon Launch will get some free T-shirts into the hands of our viewers.",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
            "subtitle": "By Garage419",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/SubaruOutbackOnStreetAndDirt.jpg",
            "title": "Subaru Outback On Street And Dirt"
        },
        {
            "description": "Tears of Steel was realized with crowd-funding by users of the open source 3D creation tool Blender. Target was to improve and test a complete open and free pipeline for visual effects in film - and to make a compelling sci-fi film in Amsterdam, the Netherlands.  The film itself, and all raw material used for making it, have been released under the Creatieve Commons 3.0 Attribution license. Visit the tearsofsteel.org website to find out more about this, or to purchase the 4-DVD box with a lot of extras.  (CC) Blender Foundation - https://www.tearsofsteel.org",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            "subtitle": "By Blender Foundation",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg",
            "title": "Tears of Steel"
        },
        {
            "description": "The Smoking Tire heads out to Adams Motorsports Park in Riverside, CA to test the most requested car of 2010, the Volkswagen GTI. Will it beat the Mazdaspeed3's standard-setting lap time? Watch and see...",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4",
            "subtitle": "By Garage419",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/VolkswagenGTIReview.jpg",
            "title": "Volkswagen GTI Review"
        },
        {
            "description": "The Smoking Tire is going on the 2010 Bullrun Live Rally in a 2011 Shelby GT500, and posting a video from the road every single day! The only place to watch them is by subscribing to The Smoking Tire or watching at BlackMagicShine.com",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            "subtitle": "By Garage419",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/WeAreGoingOnBullrun.jpg",
            "title": "We Are Going On Bullrun"
        },
        {
            "description": "The Smoking Tire meets up with Chris and Jorge from CarsForAGrand.com to see just how far $1,000 can go when looking for a car.The Smoking Tire meets up with Chris and Jorge from CarsForAGrand.com to see just how far $1,000 can go when looking for a car.",
            "sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",
            "subtitle": "By Garage419",
            "thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/WhatCarCanYouGetForAGrand.jpg",
            "title": "What care can you get for a grand?"
        }
    ]
}
```
