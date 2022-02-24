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
            
            BasicTextField(error: profileViewModel.profileState == DataState.Error(error: NetworkError.ParsingError), placeHolderText: "아이디", value: $id)
                .padding(.top)
            
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
            .padding(.init(top: 4, leading: 24, bottom: 16, trailing: 24))
            
            TextButton(text: "문제 추천", loading: false) {
                sheetManager.sheetAction = .ProblemRecommend
                sheetManager.isPresent = true
            }
            .padding()
            
            Spacer()
            BottomSheetButtonView()
        }
        .disabled(profileViewModel.profileState == DataState.Loading)
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
    
    struct BottomSheetButtonView : View {
        @EnvironmentObject var sheetManager : SheetManager
        
        var body: some View {
            HStack{
                RecentIdButton {
                    sheetManager.sheetAction = .IdList
                    sheetManager.isPresent = true
                }
                Spacer()
                GuideButton {
                    sheetManager.sheetAction = .Description
                    sheetManager.isPresent = true
                }
            }
            .padding(16)
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

