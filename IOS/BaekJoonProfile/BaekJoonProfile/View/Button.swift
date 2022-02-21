//
//  Button.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/14.
//

import SwiftUI

struct TextButton: View {
    let text : String
    let loading : Bool
    let onClick:()->Void
    
    var body: some View {
        Button(action: onClick){
            if loading {
                ProgressView()
                    .foregroundColor(.blue)
            } else {
                Text(text)
                    .buttonText(textColor: .blue)
            }
        }
    }
}

struct RecentIdButton : View {
    let onClick : () -> Void
    
    var body: some View {
        Button(action:onClick) {
            HStack {
                Image(systemName: "person.crop.rectangle.stack.fill")
                    .foregroundColor(.white)
                    .imageScale(.large)
                    .frame(width: 30, height: 30, alignment: .center)
                    .padding(.init(top: 8, leading: 4, bottom: 8, trailing: 8))
                Text("최근 조회내역")
                    .foregroundColor(.white)
                    .captionText(textColor: .white)
            }
        }
    }
}

struct GuideButton : View {
    let onClick : () -> Void
    
    var body: some View {
        Button(action:onClick){
            HStack{
                Image(systemName: "questionmark.circle.fill")
                    .foregroundColor(.white)
                    .imageScale(.large)
                    .frame(width: 30, height: 30, alignment: .center)
                    .overlay(Circle().stroke(Color.white, lineWidth: 3))
                    .padding(.init(top: 8, leading: 4, bottom: 8, trailing: 8))
                Text("위젯 추가 안내")
                    .foregroundColor(.white)
                    .captionText(textColor: .white)
            }
        }
    }
}



struct CloseButton : View {
    let onClick : () -> Void
    var body: some View {
        Button(action:onClick ) {
            Image(systemName: "xmark.circle.fill")
                .foregroundColor(.gray)
                .imageScale(.large)
                .padding(8)
        }
        
        
    }
}

struct ButtonPreview : PreviewProvider {
    static var previews: some View {
        VStack {
            TextButton(text:"프로필 조회", loading: true){
            }
            .padding()
            TextButton(text: "프로필 조회", loading: false, onClick: {})
                .padding()
            GuideButton{}
            .padding()
            CloseButton{}
            .padding()
            RecentIdButton{}
            .padding()
        }
        .frame(maxWidth:.infinity,maxHeight: .infinity)
        .background(Color.backgroundColor)
        .edgesIgnoringSafeArea(.all)
        
        
    }
}
