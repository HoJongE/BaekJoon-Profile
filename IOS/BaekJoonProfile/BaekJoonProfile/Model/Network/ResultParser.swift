//
//  ResultParser.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import Foundation


struct ResultParser {
    
    public func judgeStatus<T: Decodable>(by statusCode:Int, _ data: Data,of type : T.Type) -> DataState<T> {
        switch statusCode {
        case 200: return isValidData(data:data) as DataState<T>
        default: return DataState.Error(error: NetworkError.DefaultError)
            
        }
    }
    
    private func isValidData<T : Decodable>(data: Data) -> DataState<T> {
        let decoder = JSONDecoder()
        
        guard let decodeData = try? decoder.decode(T.self, from: data) else {return .Error(error: NetworkError.ParsingError)}
        
        return .Success(data: decodeData)
    }
}
