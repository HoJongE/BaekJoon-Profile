//
//  ClassBadgeStreak.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import SwiftUI

extension ProfileHost {
  struct ClassBadgeStreakView : View {
    let width : CGFloat
    let profile : Profile
    var body: some View {
      
      return HStack(alignment:.top) {
        VStack{
          Text("CLASS")
            .bodyText(textColor: .white)
          SquareImage(url: profile.classUrl)
            .padding(.horizontal,24)
        }
        .frame(width:width*0.33)
        VStack {
          Text("BADGE")
            .bodyText(textColor: .white)
          SquareImage(url: profile.badgeImage)
            .padding(.horizontal,24)
          
        }
        .frame(width:width*0.33)
        VStack {
          Text("STREAK")
            .bodyText(textColor: .white)
          Text(String(profile.maxStreak))
            .bodyText(textColor: Profile.getTierColor(tier: profile.tier))
            .frame(width:width*0.33,height:width*0.33 - 48)
          
        }
        .frame(width:width*0.33)
      }
      .frame(width:width)
    }
  }
}

struct ClassBadgeStreak_Previews: PreviewProvider {
  static var previews: some View {
    ProfileHost.ClassBadgeStreakView(width: 300, profile: Profile.provideDummyData())
      .previewLayout(.sizeThatFits)
      .background(Color.backgroundColor)
  }
}
