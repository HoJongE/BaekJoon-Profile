//
//  ProfileWidgetView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/18.
//

import SwiftUI
import WidgetKit

struct ProfileWidgetEntryView : View {
  @Environment(\.widgetFamily) var family : WidgetFamily
  var profileState : DataState<Profile>
  var body: some View {
    switch profileState {
      case .Empty:
        ErrorView(error: NetworkError.DefaultError)
      case .Loading:
        ErrorView(error: NetworkError.DefaultError)
      case .Error(error: let error):
        ErrorView(error: error)
      case .Success(data: let data):
        if case WidgetFamily.systemSmall = family {
          SmallProfileView(profile: data)
        } else {
          ProfileView(profile: data)
        }
    }
  }
}


struct SmallProfileView : View {
  let profile : Profile
  var body: some View {
    
    GeometryReader { geo in
      ProfileImage(url: profile.profileImage, width: geo.size.width*0.8, tier: profile.tier)
    }
    .widgetURL(URL(string: Const.URL.widgetURL(id: profile.handle))!)
  }
}
struct ProfileView : View {
  let profile : Profile
  var body: some View {
    GeometryReader { geo in
      Link(destination: URL(string: Const.URL.widgetURL(id: profile.handle))!){
        HStack(alignment:.center) {
          ProfileImage(url: profile.profileImage ,width: geo.size.width*0.25, tier: profile.tier)
          ProfileDescriptionView(profile: profile)
          Spacer()
        }
        .frame(width: geo.size.width, height: geo.size.height, alignment: .center)
        .background(Color.backgroundColor)
      }
    }
  }
}
struct ProfileDescriptionView : View {
  let profile : Profile
  var body: some View {
    VStack(spacing:24) {
      HStack (spacing: 16){
        Text(profile.handle)
          .font(.custom("GmarketSansTTFBold", size: 16))
          .shadow(radius: 3)
        Text(Profile.getTierName(tier: profile.tier))
          .font(.system(size: 16))
          .foregroundColor(Profile.getTierColor(tier: profile.tier))
          .italic()
          .shadow(radius: 3)
        Spacer()
      }
      .foregroundColor(.white)
      
      HStack(spacing:16) {
        DescriptionText(title: "풀이 수", content: String(profile.solvedCount))
        DescriptionText(title: "랭킹", content: String(profile.rank))
        DescriptionText(title: "스트릭", content: String(profile.maxStreak))
        Spacer()
      }
    }
  }
}

struct DescriptionText : View {
  let title : String
  let content : String
  var body: some View {
    VStack {
      Text(title)
        .font(.custom("GmarketSansTTFBold", size: 16))
        .padding(.bottom,4)
      Text(content)
        .font(.custom("GmarketSansTTFMedium", size: 15))
      
    }
    .foregroundColor(.white)
  }
}
struct ProfileImage: View {
  let url : String
  let width : CGFloat
  let tier : Int
  var body: some View {
    ZStack(alignment:.bottom) {
      NetworkImage(width: width, url: URL(string: url))
        .padding()
      TierBadge(width: width*0.2, tier: tier)
        .offset(y:-15)
    }
  }
}
struct ErrorView : View{
  let error : Error
  var body: some View {
    ZStack {
      Text(error.localizedDescription)
        .bodyText(textColor: .white)
    }
    .multilineTextAlignment(.center)
    .frame(maxWidth:.infinity,maxHeight: .infinity,alignment: .center)
    .background(Color.backgroundColor)
  }
}

#if DEBUG
struct ProfileWidget_Previews: PreviewProvider {
  static var previews: some View {
    ProfileWidgetEntryView(profileState: DataState.Success(data: Profile.provideDummyData()))
      .previewContext(WidgetPreviewContext(family: .systemMedium))
    
    ProfileWidgetEntryView(profileState: DataState.Error(error: NetworkError.DefaultError))
      .previewContext(WidgetPreviewContext(family: .systemMedium))
    ProfileWidgetEntryView(profileState: DataState.Success(data: Profile.provideDummyData()))
      .previewContext(WidgetPreviewContext(family: .systemSmall))
    
    ProfileWidgetEntryView(profileState: DataState.Error(error: NetworkError.DefaultError))
      .previewContext(WidgetPreviewContext(family: .systemSmall))
  }
}
#endif



struct NetworkImage: View {
  let width : CGFloat
  let url: URL?
  var body: some View {
    Group {
      if let url = url, let imageData = try? Data(contentsOf: url),
         let uiImage = UIImage(data: imageData) {
        // 추가적인 이미지 처리는 여기서 가능하다.
        Image(uiImage: uiImage)
          .resizable()
          .frame(width: width, height: width, alignment: .center)
          .clipShape(Circle())
          .overlay(Circle().stroke(Color.white,lineWidth: 1))
          .shadow(radius: 3)
      }
      else {
        // 추가적인 이미지 처리는 여기서 가능하다.
        Image("placeholder-image")
      }
    }
    
  }
}
