export interface ActiveOrder {
  listingId: number;
  chefId: number;
  chefName: string;
  chefStreet: string;
  chefPostalCode: string;
  chefCity: string;
  recipeName: string;
  image: string;
  totalPrice: number; // FIX: do some backend maths
  numberOfGuests: number;
  readyBy: string;
  purchaseDeadline: string;
}