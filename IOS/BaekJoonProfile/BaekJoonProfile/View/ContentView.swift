//
//  ContentView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/10.
//

import SwiftUI
/*:
 # Main View Container
 */
struct ContentView: View {
    @State var id:String = ""
    
    var body: some View {
       
        VStack(alignment:.center) {
            
            Text("백준\n프로필")
                .modifier(LargeTitle())
                .frame(maxWidth:.infinity)
                .multilineTextAlignment(.center)
            Image("AppLogo")
                .padding(.all,24)
            Text("solved.ac 아이디를\n입력해주세요")
                .modifier(BodyText())
                .multilineTextAlignment(.center)
            BasicTextField(error: false, placeHolderText: "아이디", value: $id)
                .padding(.vertical,24)
            
        }
        .frame(maxHeight:.infinity)
        .background(Color.backgroundColor)
        .edgesIgnoringSafeArea(.all)
        .preferredColorScheme(.dark)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        Group{
            ContentView()
                .previewDevice(PreviewDevice(rawValue: "iPhone 12 Pro"))
                .previewDisplayName("iPhone 12 pro")
            ContentView()
                .previewDevice(PreviewDevice(rawValue: "iPhone 12 Pro"))
                .previewDisplayName("iPhone 12 pro")
                .environment(\.colorScheme, .dark)
        }
    }
}
