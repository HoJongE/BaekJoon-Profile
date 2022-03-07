//
//  LoingView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/16.
//

import SwiftUI

struct LoginView: View {
    @State private var id:String = "as00098"
    @EnvironmentObject var profileViewModel : ProfileViewModel
    @EnvironmentObject var sheetManager : SheetManager

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
            
            BasicTextField(error: profileViewModel.profileState.error != nil , placeHolderText: "아이디", value: $id)
                .padding(.top)
            
            if let error = profileViewModel.profileState.error {
                Text(error.localizedDescription)
                    .captionText(textColor: .errorColor)
            } else {
                Text("ㅎㅎㅎ")
                    .captionText(textColor: .backgroundColor)
            }
            
            TextButton(text: "프로필 조회",loading: profileViewModel.profileState.loading, onClick: {
                profileViewModel.getProfile(id: id)
            })
            .padding(.init(top: 16, leading: 24, bottom: 16, trailing: 24))
            
            BottomSheetActionButton()
                .padding()
            
            Spacer()
        }
        .disabled(profileViewModel.profileState.loading)
        .frame(maxHeight:.infinity)
        .background(Color.backgroundColor.edgesIgnoringSafeArea(.all))
        .preferredColorScheme(.dark)
        .sheet(isPresented: $sheetManager.isPresent) {
            BottomSheetContainer(title: sheetManager.sheetAction.rawValue, isPresent: $sheetManager.isPresent){
                switch sheetManager.sheetAction {
                    case .Description:
                        GuideView()
                    case .IdList:
                        IdListView(idList: profileViewModel.profileIdList,
                                   onDelete: onDelete(at:),
                                   onMove: onMove(fromSource:to:)
                        ) {id in
                            sheetManager.isPresent = false
                            profileViewModel.getProfile(id: id)
                        }
                        .background(Color.backgroundColor)
                    case .ProblemRecommend:
                        ProblemHost()
                }
            }
        }
    }
}

extension LoginView {
    func onDelete(at indexSet: Optional<IndexSet>) {
        profileViewModel.deleteId(indexSet: indexSet)
    }
    func onMove(fromSource : IndexSet, to destination : Int) {
        profileViewModel.moveId(from: fromSource, to: destination)
        
    }
}

struct LoingView_Previews: PreviewProvider {
    static var previews: some View {
        let profileViewModel = ProfileViewModel()
        let sheetManager = SheetManager()
        Group {
            ForEach(PreviewDevice.previewDevices,id: \.self) { previewDevice in
                LoginView()
                    .environmentObject(profileViewModel)
                    .environmentObject(sheetManager)
                    .previewDevice(PreviewDevice(rawValue: previewDevice))
                    .previewDisplayName(previewDevice)
            }
        }
        
    }
}

