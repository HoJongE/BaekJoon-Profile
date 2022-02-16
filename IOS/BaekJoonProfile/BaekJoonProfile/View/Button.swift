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
            } else {
                Text(text)
                    .modifier(ButtonText())
            }
        }
    }
}

struct Button_Previews: PreviewProvider {
    static var previews: some View {
        TextButton(text:"프로필 조회", loading: true){
            
        }
        TextButton(text: "프로필 조회", loading: false, onClick: {})
    }
}
