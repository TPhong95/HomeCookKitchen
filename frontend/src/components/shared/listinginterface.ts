export interface ListingStruct {
  id: number;
  image: string;
  recipeName: string; // Join table?
  description?: string;
  quantity: number;
  quantityPurchased: number;
  unitPrice: number;
  createdAt: string;
  purchaseDeadline: string;
  readyBy: string;
}