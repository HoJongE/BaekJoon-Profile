//
//  TopBar.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import SwiftUI

struct BottomButton : View {
    let logout: () -> ()
    
    var body: some View {
        Button(action: logout){
            ZStack {
                RoundedRectangle(cornerRadius: 8)
                    .foregroundColor(.white)
                    .frame(maxWidth:.infinity, maxHeight: 50, alignment: .center)
                Text("닫기")
                    .buttonText(textColor: .black)
            }
        }
        .padding(.init(top: 8, leading: 24, bottom: 8, trailing: 24))
    }
}

struct TopBarPreview : PreviewProvider {
    static var previews: some View {
        BottomButton {
            
        }.background(Color.black)
            .previewLayout(.sizeThatFits)
            .previewDisplayName("탑 바")
    }
}
