export interface ListingStruct {
  productListingId: number;

  recipeId: number;
  recipeName: string;
  recipeUrl: string;
  recipeDescription: string;
  addressId: number;
  compositeAddress: string;
  chefId: number;
  chefName: string;
  active: boolean;
  quantityOffered: number;
  quantityPurchased: number;
  unitPrice: number;
  createdAtTime: string;
  purchaseDeadline: string;
  readyBy: string;
}