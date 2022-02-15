//
//  ProfileView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/15.
//

import SwiftUI
import SDWebImageSwiftUI

struct ProfileView: View {
    
    var body: some View {
        GeometryReader { geo in
            VStack(alignment:.center){
                BackgroundView(height: geo.size.height/5)
                CircleImage(url : "https://static.solved.ac/uploads/profile/360x360/2dbfa96246ee0c02cf13a756d5ddd0ffb0ef978e.png",width: geo.size.width/3)
                    .offset(y:-70)
                    .padding(.bottom,-56)
                Text("as00098")
                    .modifier(BodyText(textColor: .white))
                ProfileDescription(text: "안녕하세\ndd\ndddd")
                ClassBadgeStreakView(width: geo.size.width-32)
                    .padding(8)
                VerticalInfoView(solved: 517, rating: 1908, rank: 4987,width: geo.size.width-32,tier: 3)
                Spacer()
                
            }
            .frame(maxWidth:.infinity,maxHeight: .infinity)
            .background(Color.backgroundColor)
            .edgesIgnoringSafeArea(.all)
            .preferredColorScheme(.dark)
            
        }
        
    }
}

struct ProfileDescription : View {
    let text : String
    var body: some View {
        
        Text(text)
            .frame(maxWidth:.infinity,alignment: .leading)
            .modifier(CaptionText())
            .padding()
            .background(Color.white)
            .cornerRadius(8)
            .padding(.horizontal,32)
            .padding(.vertical,8)
        
    }
}

struct BackgroundView : View {
    let height : CGFloat
    var body: some View {
        WebImage(url: URL(string :"https://solved.ac/_next/image?url=https%3A%2F%2Fstatic.solved.ac%2Fprofile_bg%2F_season2020%2Fs2020-gold4.png&w=3840&q=75"))
            .resizable()
            .indicator(.activity)
            .scaledToFit()
            .aspectRatio(3, contentMode: .fit)
            .frame(maxWidth:.infinity)
        
    }
}
struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
            .previewDevice(PreviewDevice(rawValue: "iPhone 12 Pro"))
        ProfileView()
            .previewDevice(PreviewDevice(rawValue: "iPhone 8"))
    }
}

struct ClassBadgeStreakView : View {
    let width : CGFloat
    var body: some View {
        HStack(alignment:.top) {
            VStack{
                Text("CLASS")
                    .modifier(BodyText(textColor: .white))
                SquareImage(url: "https://static.solved.ac/class/c5.svg")
            }
            .frame(width:width*0.33)
            VStack {
                Text("BADGE")
                    .modifier(BodyText(textColor: .white))
                SquareImage(url: "https://static.solved.ac/class/c5.svg")
            }
            .frame(width:width*0.33)
            VStack {
                Text("STREAK")
                    .modifier(BodyText(textColor: .white))
                Text("32")
                    .modifier(BodyText(textColor: .white))
                    .frame(width:width*0.33,height:width*0.33)
                    
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
    let tier : Int
    let colors = Color.tierColors
    var body: some View {
        VStack(alignment:.leading,spacing: 12) {
            Text("Solved")
                .modifier(BodyText(textColor: .white))
            Text(String(solved))
                .modifier(BodyText(textColor: colors[tier]))
                .padding(.bottom)
            Text("AC Rating")
                .modifier(BodyText(textColor: .white))
            Text(String(rating))
                .modifier(BodyText(textColor: colors[tier]))
                .padding(.bottom)
            Text("AC Rating")
                .modifier(BodyText(textColor: .white))
            Text(String(rating))
                .modifier(BodyText(textColor: colors[tier]))
        }
        .frame(width:width,alignment: .leading)
        .padding(.leading,32)
        
    }
}
