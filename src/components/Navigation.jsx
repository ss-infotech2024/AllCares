import { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { Button } from "../components/ui/Button.jsx";
import { Badge } from "../components/ui/badge";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "../components/ui/sheet";
import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger,
} from "../components/ui/NavigationMenu";
import { useCart } from "../context/CartContext.jsx";
import { categories } from "../data/products.js";
import {
  Heart,
  Menu,
  Search,
  ShoppingCart,
  User,
  Phone,
  Shield,
  Award,
} from "lucide-react";
import { cn } from "../lib/Utils.js";

export function Navigation() {
  const [isOpen, setIsOpen] = useState(false);
  const { itemCount } = useCart();
  const location = useLocation();

  const isActive = (path) => location.pathname === path;

  return (
    <header className="border-b bg-white sticky top-0 z-50">
      {/* Top Banner */}
      <div className="bg-primary text-primary-foreground py-2">
        <div className="container mx-auto px-4">
          <div className="flex items-center justify-center text-sm font-medium">
            <div className="flex items-center gap-6">
              <div className="flex items-center gap-2">
                <Shield className="h-4 w-4" />
                <span>FDA Approved Products</span>
              </div>
              <div className="flex items-center gap-2">
                <Award className="h-4 w-4" />
                <span>ISO 13485 Certified</span>
              </div>
              <div className="flex items-center gap-2">
                <Phone className="h-4 w-4" />
                <span>24/7 Support: 1-800-MEDICAL</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Main Navigation */}
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          {/* Logo */}
          <Link to="/" className="flex items-center gap-2">
            <div className="h-10 w-10 bg-primary rounded-lg flex items-center justify-center">
              <span className="text-primary-foreground font-bold text-xl">
                M
              </span>
            </div>
            <div>
              <h1 className="font-display font-bold text-xl text-foreground">
                MedSupply
              </h1>
              <p className="text-xs text-muted-foreground">
                Professional Healthcare
              </p>
            </div>
          </Link>

          {/* Desktop Navigation */}
          <NavigationMenu className="hidden lg:flex">
            <NavigationMenuList>
              {[
                { label: "Home", path: "/" },
                { label: "Shop", path: "/shop" },
                { label: "About", path: "/about" },
              ].map((item) => (
                <NavigationMenuItem key={item.path}>
                  <Link
                    to={item.path}
                    className={cn(
                      "group inline-flex h-10 w-max items-center justify-center rounded-md bg-background px-4 py-2 text-sm font-medium transition-colors hover:bg-accent hover:text-accent-foreground",
                      isActive(item.path) && "bg-accent text-accent-foreground"
                    )}
                  >
                    {item.label}
                  </Link>
                </NavigationMenuItem>
              ))}

              {/* Categories Dropdown */}
              <NavigationMenuItem>
                <NavigationMenuTrigger>Categories</NavigationMenuTrigger>
                <NavigationMenuContent>
                  <div className="grid w-[600px] grid-cols-2 gap-3 p-6">
                    {categories.map((category) => (
                      <NavigationMenuLink key={category.id} asChild>
                        <Link
                          to={`/shop?category=${category.id}`}
                          className="block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground"
                        >
                          <div className="flex items-center gap-3">
                            <span className="text-2xl">{category.icon}</span>
                            <div>
                              <div className="text-sm font-medium leading-none">
                                {category.name}
                              </div>
                              <p className="line-clamp-2 text-sm leading-snug text-muted-foreground">
                                {category.description}
                              </p>
                            </div>
                          </div>
                        </Link>
                      </NavigationMenuLink>
                    ))}
                  </div>
                </NavigationMenuContent>
              </NavigationMenuItem>
            </NavigationMenuList>
          </NavigationMenu>

          {/* Right Side Actions */}
          <div className="flex items-center gap-2">
            {[Search, Heart, User].map((Icon, i) => (
              <Button
                key={i}
                variant="ghost"
                size="icon"
                className="hidden md:flex"
              >
                <Icon className="h-5 w-5" />
              </Button>
            ))}
            <Button variant="ghost" size="icon" className="relative" asChild>
              <Link to="/cart">
                <ShoppingCart className="h-5 w-5" />
                {itemCount > 0 && (
                  <Badge
                    variant="destructive"
                    className="absolute -top-2 -right-2 h-5 w-5 flex items-center justify-center p-0 text-xs"
                  >
                    {itemCount}
                  </Badge>
                )}
              </Link>
            </Button>

            {/* Mobile Navigation */}
            <Sheet open={isOpen} onOpenChange={setIsOpen}>
              <SheetTrigger asChild>
                <Button variant="ghost" size="icon" className="lg:hidden">
                  <Menu className="h-5 w-5" />
                </Button>
              </SheetTrigger>
              <SheetContent side="right" className="w-80">
                <SheetHeader>
                  <SheetTitle>Navigation</SheetTitle>
                </SheetHeader>
                <nav className="flex flex-col gap-4 mt-6">
                  {[
                    { label: "Home", path: "/" },
                    { label: "Shop", path: "/shop" },
                    { label: "About", path: "/about" },
                  ].map((item) => (
                    <Link
                      key={item.path}
                      to={item.path}
                      className="text-lg font-medium"
                      onClick={() => setIsOpen(false)}
                    >
                      {item.label}
                    </Link>
                  ))}
                  <div className="space-y-2">
                    <h3 className="text-lg font-medium">Categories</h3>
                    {categories.map((category) => (
                      <Link
                        key={category.id}
                        to={`/shop?category=${category.id}`}
                        className="block text-muted-foreground hover:text-foreground ml-4"
                        onClick={() => setIsOpen(false)}
                      >
                        {category.icon} {category.name}
                      </Link>
                    ))}
                  </div>
                </nav>
              </SheetContent>
            </Sheet>
          </div>
        </div>
      </div>
    </header>
  );
}
