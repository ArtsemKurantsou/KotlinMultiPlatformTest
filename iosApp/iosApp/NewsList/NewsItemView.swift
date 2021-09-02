//
//  NewsItemView.swift
//  iosApp
//
//  Created by Artem on 31.08.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NewsItemView: View {
    
    var item: ViewNewsItem
    
    var body: some View {
        VStack(spacing: 8) {
            Text(item.title ?? "")
                .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                .lineLimit(2)
            HStack {
                Text(item.source)
                Spacer()
                Text(item.publishDate)
            }
            if (item.imageUrl != nil) {
                AsyncImage(
                    url: URL(string: item.imageUrl!)!,
                    placeholder: { ProgressView() },
                    image: { uiImage in
                        Image(uiImage: uiImage)
                            .resizable()
                    }
                ).frame(idealHeight: 150)
            }
        }
    }
}

#if DEBUG
struct NewsItemView_Previews: PreviewProvider {
    static var previews: some View {
        let item = ViewNewsItem(
            id: 1,
            url: "url to the news item",
            title: "Some news item title",
            source: "Fox News",
            publishDate: "04.01.1997 15:23",
            imageUrl: "https://www.kindpng.com/picc/m/565-5652618_food-artwork-pusheen-drawing-cat-free-hd-image.png"
        )
        NewsItemView(item: item)
    }
}
#endif
