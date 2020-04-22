export interface Emoji {
	name: string;
	code: string;
	source: string;
}

export interface EmojiSection {
	sectionName: string;
	emojiList: Emoji[];
}
