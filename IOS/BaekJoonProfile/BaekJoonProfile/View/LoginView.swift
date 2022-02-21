//
//  LoingView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/16.
//

import SwiftUI

struct LoginView: View {
    @State var id:String = "as00098"
    @EnvironmentObject var profileViewModel : ProfileViewModel
    
    var body: some View {
        
        VStack(alignment:.center) {
            Spacer().frame(height:60)
            Text("백준\n프로필")
                .largeTitle(textColor: .firstTextColor)
                .frame(maxWidth:.infinity)
                .multilineTextAlignment(.center)
            
            Image("AppLogo")
                .padding(.all,24)
            
            Text("solved.ac 아이디를\n입력해주세요")
                .bodyText(textColor: .firstTextColor)
                .multilineTextAlignment(.center)
            
            BasicTextField(error: profileViewModel.profileState == DataState.Error(error: ProfileError.ParsingError), placeHolderText: "아이디", value: $id)
                .padding(.vertical,24)
            
            if case let DataState.Error(error: error) = profileViewModel.profileState {
                Text(error.localizedDescription)
                    .captionText(textColor: .errorColor)
            } else {
                Text("ㅎㅎㅎ")
                    .foregroundColor(.backgroundColor)
            }
            
            
            TextButton(text: "프로필 조회",loading: profileViewModel.profileState == DataState.Loading , onClick: {
                profileViewModel.getProfile(id: id)
            })
            .padding(.init(top: 16, leading: 24, bottom: 24, trailing: 24))
            Spacer()
        }
        .disabled(profileViewModel.profileState == DataState.Loading)
        .frame(maxHeight:.infinity)
        .background(Color.backgroundColor.edgesIgnoringSafeArea(.all))
        .preferredColorScheme(.dark)
    }
}

struct LoingView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
            .environmentObject(ProfileViewModel())
    }
}

