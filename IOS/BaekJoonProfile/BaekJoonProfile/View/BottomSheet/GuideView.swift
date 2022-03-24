//
//  GuideView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/20.
//

import SwiftUI


struct GuideView : View {
  
  var body: some View {
    ScrollView(.vertical,showsIndicators: true){
      VStack(alignment:.center,spacing: 16) {
        
        GuideInfoView(step: 1, description: "홈 화면에서 앱이 흔들릴 때까지 빈 영역이나 앱을 길게 터치합니다. 그리고 왼쪽 상단 모서리의 + 버튼을 터치합니다.", imageString: "GuideImage_1")
        GuideInfoView(step: 2, description: "백준 프로필을 선택하고 '위젯 추가' 버튼을 터치합니다.", imageString: "GuideImage_2")
        GuideInfoView(step: 3, description: "홈 화면 편집 모드에서(앱이 좌우로 흔들리는) 위젯을 선택하거나, 위젯을 길게 눌러 '위젯 편집' 버튼을 터치합니다.", imageString: "GuideImage_3")
        GuideInfoView(step: 4, description: "조회를 원하는 solved.ac 아이디를 입력해주세요. 위젯 업데이트 간격을 조절할려면 업데이트 간격을 직접 입력해주세요. 간격이 짧을 수록 최신화는 잘되지만 배터리는 빨리 소모됩니다.", imageString: "GuideImage_4")
      }
    }
  }
}

struct GuideInfoView : View {
  let step : Int
  let description : String
  let imageString : String
  
  var body: some View {
    VStack(alignment:.center){
      HStack{
        Text("스텝 \(step)")
          .font(.custom("GmarketSansTTFBold", size: 18))
          .padding()
        Spacer()
      }
      HStack{
        Text(description)
          .captionText(textColor: .white)
          .multilineTextAlignment(.leading)
          .padding(.horizontal)
        
        Spacer()
      }
      Image(imageString)
        .resizable()
        .scaledToFit()
        .frame(width: 200)
        .padding()
    }
    
  }
}
struct GuideView_Previews: PreviewProvider {
  static var previews: some View {
    BottomSheetContainer(title: "위젯 안내 가이드", isPresent: .constant(true)){
      GuideView()
    }
  }
}


