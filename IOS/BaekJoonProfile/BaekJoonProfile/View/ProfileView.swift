//
//  ProfileView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/15.
//

import SwiftUI
import SDWebImageSwiftUI

struct ProfileView: View {
    let profile : Profile
    let logout:()->()
    
    var body: some View {
        
        GeometryReader { geo in
            
            VStack(alignment:.center){
                TopBar {
                    logout()
                }
                ScrollView(.vertical, showsIndicators: false){
                    BackgroundView(height: geo.size.height/5,url: profile.backgroundImage ,width: geo.size.width)
                    
                    ProfileImage(url : profile.profileImage , tier: profile.tier ,width: geo.size.width/3)
                        .offset(y:-70)
                        .padding(.bottom,-56)
                    
                    Text(profile.handle)
                        .bodyText(textColor: .white)
                    
                    ProfileDescription(text: profile.selfDescription)
                    
                    ClassBadgeStreakView(width: geo.size.width-32, profile: profile)
                        .padding(8)
                    
                    VerticalInfoView(solved: profile.solvedCount, rating: profile.rating, rank: profile.rank ,width: geo.size.width-32, textColor: Profile.getTierColor(tier: profile.tier))
                    
                    Spacer()
                }
            }
        }
        .frame(maxWidth:.infinity,maxHeight: .infinity)
        .background(Color.backgroundColor.edgesIgnoringSafeArea(.all))
        .preferredColorScheme(.dark)
        
    }
}

struct ProfileDescription : View {
    let text : String
    var body: some View {
        
        Text(text)
            .captionText(textColor: .black)
            .frame(maxWidth:.infinity,alignment: .leading)
            .padding()
            .background(Color.white)
            .cornerRadius(8)
            .padding(.horizontal,32)
            .padding(.vertical,8)
        
    }
}

struct BackgroundView : View {
    let height : CGFloat
    let url : String
    let width: CGFloat
    var body: some View {
        WebImage(url: URL(string : url))
            .resizable()
            .indicator(.activity)
            .scaledToFit()
            .frame(width: width,height: width * 0.33)
        
    }
}
struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView(profile : Profile.provideDummyData()){}
        .previewDevice(PreviewDevice(rawValue: "iPhone 12 Pro"))
        
        ProfileView(profile : Profile.provideDummyData()){}
        .previewDevice(PreviewDevice(rawValue: "iPhone 8"))
        
    }
}

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

struct VerticalInfoView : View {
    let solved : Int
    let rating : Int
    let rank : Int
    let width : CGFloat
    let textColor : Color
    var body: some View {
        VStack(alignment:.leading,spacing: 12) {
            Text("Solved")
                .bodyText(textColor: .white)
            Text(String(solved))
                .bodyText(textColor: textColor)
                .padding(.bottom)
            Text("AC Rating")
                .bodyText(textColor: .white)
            
            Text(String(rating))
                .bodyText(textColor: textColor)
                .padding(.bottom)
            Text("Rank")
                .bodyText(textColor: .white)
            Text(String(rank))
                .bodyText(textColor: textColor)
        }
        
        .frame(width:width,alignment: .leading)
        .padding(.leading,32)
        
    }
}

struct TopBar : View {
    let logout: () -> ()
    
    var body: some View {
        Button(action: logout){
            HStack(alignment:.lastTextBaseline){
                Image(systemName: "arrow.backward")
                    .foregroundColor(.white)
                    .padding(.init(top: 0, leading: 16, bottom: 8, trailing: 8))
                Text("뒤로 가기")
                    .modifier(BodyTextModifier(textColor: .white))
                Spacer()
            }
        }
    }
}

struct TopBarPreview : PreviewProvider {
    static var previews: some View {
        TopBar {
            
        }.background(Color.black)
    }
}

struct ProfileImage : View {
    let url : String
    let tier : Int
    let width : CGFloat
    var body: some View {
        ZStack(alignment:.bottom) {
            CircleImage(url: url, width: width)
            TierBadge(width: width/5, tier: tier)
                .offset(y:5)
        }
    }
}
